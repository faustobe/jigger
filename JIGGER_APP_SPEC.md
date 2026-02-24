# JIGGER - Project Specification

**Quick Reference:**
- **App Name:** Jigger
- **Package:** it.faustobe.jigger
- **Language:** Java
- **Platform:** Android (min SDK 24, target SDK 34)
- **Architecture:** MVVM + Repository Pattern
- **Database:** Room (SQLite)
- **Developer:** Fausto Bernardini (faustobe)

---

## PROJECT OVERVIEW

### Application Name
**Jigger** - Shift tracking and inventory management for professional bartenders

**Package Name:** `it.faustobe.jigger`

### Purpose
Android application for bartenders/mixologists to:
1. **Primary**: Track cocktails served during work shifts in real-time
2. **Secondary**: Manage bar inventory and ingredient stock levels
3. **Scalability**: Start as personal tool, expand to team/bar management system

### App Identity & Branding
**Tagline:** *"Measure every shift, perfect your craft"*

**Brand Positioning:**
- Professional tool for serious bartenders
- Emphasis on precision (like a jigger) and efficiency
- Clean, modern, no-nonsense design
- Built by bartenders, for bartenders

**Visual Identity Direction:**
- Icon: Stylized jigger silhouette (minimalist, recognizable)
- Colors: Professional yet approachable (avoid overly playful)
- Typography: Clean, readable in bar environment
- UI Style: Material Design with custom touches

### Target User
Professional bartender working in fast-paced bar environment requiring quick, reliable shift tracking with minimal interaction time (<3 seconds per cocktail entry).

---

## TECHNICAL STACK

### Required Technologies
- **Language**: Java (NOT Kotlin)
- **UI Framework**: Jetpack Compose (Java-compatible) OR XML layouts with ViewBinding
- **Database**: Room Persistence Library
- **Architecture**: MVVM (Model-View-ViewModel) with Repository pattern
- **Async Operations**: Java Executors or RxJava
- **DI (optional)**: Dagger/Hilt or manual DI
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

### Project Structure
```
app/
├── src/main/java/it/faustobe/jigger/
│   ├── data/
│   │   ├── local/
│   │   │   ├── dao/
│   │   │   ├── database/
│   │   │   └── entities/
│   │   ├── repository/
│   │   └── models/
│   ├── domain/
│   │   └── usecases/
│   ├── ui/
│   │   ├── shift/
│   │   ├── inventory/
│   │   ├── history/
│   │   └── common/
│   ├── viewmodel/
│   └── utils/
└── res/
    ├── layout/
    ├── values/
    └── drawable/
```

---

## DATA MODEL

### Database Schema (Room Entities)

#### 1. Shift Entity
```java
@Entity(tableName = "shifts")
public class Shift {
    @PrimaryKey
    @NonNull
    private String id; // UUID
    
    private String bartenderId;
    private long startTime; // Unix timestamp
    private Long endTime; // Nullable, null if shift active
    
    @ColumnInfo(name = "status")
    private String status; // "ACTIVE" or "CLOSED"
    
    private String notes;
    
    // Getters and setters
}
```

#### 2. Cocktail Entity
```java
@Entity(tableName = "cocktails")
public class Cocktail {
    @PrimaryKey
    @NonNull
    private String id;
    
    private String name;
    private String category; // "IBA", "CLASSIC", "SIGNATURE", "CUSTOM"
    private String glassType; // "HIGHBALL", "COUPE", "ROCKS", etc.
    private String imageUrl; // Nullable
    private boolean isCustom; // true if user-created
    private boolean isFavorite; // for quick access
    
    // Getters and setters
}
```

#### 3. Ingredient Entity
```java
@Entity(tableName = "ingredients")
public class Ingredient {
    @PrimaryKey
    @NonNull
    private String id;
    
    private String name;
    private String type; // "SPIRIT", "LIQUEUR", "MIXER", "GARNISH", "SYRUP", "JUICE", "OTHER"
    private String unit; // "ml", "g", "piece", "bunch"
    private float currentStock;
    private float minThreshold; // Alert threshold
    private Float costPerUnit; // Nullable, for future cost tracking
    
    // Getters and setters
}
```

#### 4. CocktailIngredient Entity (Join Table)
```java
@Entity(
    tableName = "cocktail_ingredients",
    primaryKeys = {"cocktailId", "ingredientId"},
    foreignKeys = {
        @ForeignKey(entity = Cocktail.class, parentColumns = "id", childColumns = "cocktailId"),
        @ForeignKey(entity = Ingredient.class, parentColumns = "id", childColumns = "ingredientId")
    }
)
public class CocktailIngredient {
    @NonNull
    private String cocktailId;
    
    @NonNull
    private String ingredientId;
    
    private float quantity;
    private String unit;
    
    // Getters and setters
}
```

#### 5. ShiftCocktail Entity (Tracks served cocktails)
```java
@Entity(
    tableName = "shift_cocktails",
    foreignKeys = {
        @ForeignKey(entity = Shift.class, parentColumns = "id", childColumns = "shiftId"),
        @ForeignKey(entity = Cocktail.class, parentColumns = "id", childColumns = "cocktailId")
    }
)
public class ShiftCocktail {
    @PrimaryKey
    @NonNull
    private String id; // UUID
    
    private String shiftId;
    private String cocktailId;
    private long timestamp;
    private int quantity; // Usually 1, but can batch
    
    // Getters and setters
}
```

#### 6. Bartender Entity (for Phase 2 multi-user)
```java
@Entity(tableName = "bartenders")
public class Bartender {
    @PrimaryKey
    @NonNull
    private String id;
    
    private String name;
    private long createdAt;
    
    // Getters and setters
}
```

### DAOs (Data Access Objects)

#### ShiftDao
```java
@Dao
public interface ShiftDao {
    @Insert
    void insert(Shift shift);
    
    @Update
    void update(Shift shift);
    
    @Query("SELECT * FROM shifts WHERE status = 'ACTIVE' LIMIT 1")
    Shift getActiveShift();
    
    @Query("SELECT * FROM shifts WHERE id = :shiftId")
    Shift getShiftById(String shiftId);
    
    @Query("SELECT * FROM shifts ORDER BY startTime DESC LIMIT :limit")
    List<Shift> getRecentShifts(int limit);
    
    @Query("SELECT * FROM shifts WHERE startTime >= :startDate AND startTime <= :endDate ORDER BY startTime DESC")
    List<Shift> getShiftsByDateRange(long startDate, long endDate);
}
```

#### CocktailDao
```java
@Dao
public interface CocktailDao {
    @Insert
    void insert(Cocktail cocktail);
    
    @Insert
    void insertAll(List<Cocktail> cocktails);
    
    @Update
    void update(Cocktail cocktail);
    
    @Query("SELECT * FROM cocktails ORDER BY name ASC")
    List<Cocktail> getAllCocktails();
    
    @Query("SELECT * FROM cocktails WHERE isFavorite = 1 ORDER BY name ASC")
    List<Cocktail> getFavoriteCocktails();
    
    @Query("SELECT * FROM cocktails WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    List<Cocktail> searchCocktails(String query);
    
    @Query("SELECT * FROM cocktails WHERE id = :cocktailId")
    Cocktail getCocktailById(String cocktailId);
}
```

#### IngredientDao
```java
@Dao
public interface IngredientDao {
    @Insert
    void insert(Ingredient ingredient);
    
    @Insert
    void insertAll(List<Ingredient> ingredients);
    
    @Update
    void update(Ingredient ingredient);
    
    @Query("SELECT * FROM ingredients ORDER BY name ASC")
    List<Ingredient> getAllIngredients();
    
    @Query("SELECT * FROM ingredients WHERE currentStock <= minThreshold ORDER BY name ASC")
    List<Ingredient> getLowStockIngredients();
    
    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    Ingredient getIngredientById(String ingredientId);
}
```

#### ShiftCocktailDao
```java
@Dao
public interface ShiftCocktailDao {
    @Insert
    void insert(ShiftCocktail shiftCocktail);
    
    @Query("SELECT * FROM shift_cocktails WHERE shiftId = :shiftId ORDER BY timestamp DESC")
    List<ShiftCocktail> getCocktailsByShift(String shiftId);
    
    @Query("SELECT c.*, COUNT(sc.id) as count FROM cocktails c " +
           "INNER JOIN shift_cocktails sc ON c.id = sc.cocktailId " +
           "WHERE sc.shiftId = :shiftId " +
           "GROUP BY c.id " +
           "ORDER BY count DESC")
    List<CocktailWithCount> getCocktailCountsForShift(String shiftId);
    
    @Query("DELETE FROM shift_cocktails WHERE id = :shiftCocktailId")
    void delete(String shiftCocktailId);
}
```

#### CocktailIngredientDao
```java
@Dao
public interface CocktailIngredientDao {
    @Insert
    void insert(CocktailIngredient cocktailIngredient);
    
    @Query("SELECT i.* FROM ingredients i " +
           "INNER JOIN cocktail_ingredients ci ON i.id = ci.ingredientId " +
           "WHERE ci.cocktailId = :cocktailId")
    List<Ingredient> getIngredientsForCocktail(String cocktailId);
}
```

### Database Class
```java
@Database(
    entities = {
        Shift.class,
        Cocktail.class,
        Ingredient.class,
        CocktailIngredient.class,
        ShiftCocktail.class,
        Bartender.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    
    public abstract ShiftDao shiftDao();
    public abstract CocktailDao cocktailDao();
    public abstract IngredientDao ingredientDao();
    public abstract ShiftCocktailDao shiftCocktailDao();
    public abstract CocktailIngredientDao cocktailIngredientDao();
    public abstract BartenderDao bartenderDao();
    
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "mixologist_database"
                    ).addCallback(new DatabaseCallback())
                     .build();
                }
            }
        }
        return INSTANCE;
    }
    
    // Callback to seed initial data
    private static class DatabaseCallback extends RoomDatabase.Callback {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Trigger seed data insertion
        }
    }
}
```

---

## SEED DATA

### Cocktails (IBA Official + Classics)

#### Classic Cocktails to Seed
```java
// Seed these cocktails on first run
public static List<Cocktail> getInitialCocktails() {
    List<Cocktail> cocktails = new ArrayList<>();
    
    // Unforgettables
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Negroni", "IBA", "ROCKS", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Old Fashioned", "IBA", "ROCKS", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Mojito", "IBA", "HIGHBALL", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Margarita", "IBA", "COUPE", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Dry Martini", "IBA", "MARTINI", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Daiquiri", "IBA", "COUPE", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Whiskey Sour", "IBA", "ROCKS", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Manhattan", "IBA", "COUPE", null, false, true));
    
    // Contemporary Classics
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Aperol Spritz", "IBA", "WINE", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Espresso Martini", "IBA", "COUPE", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Moscow Mule", "IBA", "COPPER_MUG", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Cosmopolitan", "IBA", "MARTINI", null, false, true));
    
    // New Era Drinks
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Gin Tonic", "CLASSIC", "HIGHBALL", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Bloody Mary", "IBA", "HIGHBALL", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Caipirinha", "IBA", "ROCKS", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Mai Tai", "IBA", "HURRICANE", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Pina Colada", "IBA", "HURRICANE", null, false, true));
    cocktails.add(new Cocktail(UUID.randomUUID().toString(), "Long Island Iced Tea", "IBA", "HIGHBALL", null, false, true));
    
    return cocktails;
}
```

### Ingredients to Seed
```java
public static List<Ingredient> getInitialIngredients() {
    List<Ingredient> ingredients = new ArrayList<>();
    
    // Spirits
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Gin", "SPIRIT", "ml", 2000, 500, 0.02f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Vodka", "SPIRIT", "ml", 2000, 500, 0.015f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Rum Bianco", "SPIRIT", "ml", 1500, 400, 0.018f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Rum Scuro", "SPIRIT", "ml", 1000, 300, 0.022f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Tequila", "SPIRIT", "ml", 1200, 400, 0.025f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Whiskey", "SPIRIT", "ml", 1500, 400, 0.03f));
    
    // Liqueurs
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Campari", "LIQUEUR", "ml", 1000, 200, 0.02f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Vermouth Rosso", "LIQUEUR", "ml", 1000, 200, 0.015f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Vermouth Dry", "LIQUEUR", "ml", 1000, 200, 0.015f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Triple Sec", "LIQUEUR", "ml", 750, 150, 0.018f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Aperol", "LIQUEUR", "ml", 1000, 200, 0.012f));
    
    // Mixers & Juices
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Succo di Limone", "JUICE", "ml", 2000, 500, 0.003f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Succo di Lime", "JUICE", "ml", 1500, 400, 0.004f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Sciroppo Zucchero", "SYRUP", "ml", 1000, 200, 0.002f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Soda", "MIXER", "ml", 5000, 1000, 0.001f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Tonic Water", "MIXER", "ml", 3000, 750, 0.002f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Ginger Beer", "MIXER", "ml", 2000, 500, 0.003f));
    
    // Garnishes
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Menta Fresca", "GARNISH", "bunch", 20, 5, 0.5f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Lime", "GARNISH", "piece", 50, 10, 0.3f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Limone", "GARNISH", "piece", 50, 10, 0.25f));
    ingredients.add(new Ingredient(UUID.randomUUID().toString(), "Arancia", "GARNISH", "piece", 30, 8, 0.4f));
    
    return ingredients;
}
```

---

## REPOSITORY PATTERN

### Repository Interface & Implementation

```java
public interface ShiftRepository {
    void startShift(Shift shift, RepositoryCallback<Shift> callback);
    void endShift(String shiftId, RepositoryCallback<Shift> callback);
    void getActiveShift(RepositoryCallback<Shift> callback);
    void recordCocktail(ShiftCocktail shiftCocktail, RepositoryCallback<Void> callback);
    void getShiftSummary(String shiftId, RepositoryCallback<ShiftSummary> callback);
}

public class ShiftRepositoryImpl implements ShiftRepository {
    private final ShiftDao shiftDao;
    private final ShiftCocktailDao shiftCocktailDao;
    private final CocktailDao cocktailDao;
    private final Executor executor;
    
    public ShiftRepositoryImpl(ShiftDao shiftDao, ShiftCocktailDao shiftCocktailDao, 
                               CocktailDao cocktailDao, Executor executor) {
        this.shiftDao = shiftDao;
        this.shiftCocktailDao = shiftCocktailDao;
        this.cocktailDao = cocktailDao;
        this.executor = executor;
    }
    
    @Override
    public void startShift(Shift shift, RepositoryCallback<Shift> callback) {
        executor.execute(() -> {
            try {
                shiftDao.insert(shift);
                callback.onSuccess(shift);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }
    
    @Override
    public void endShift(String shiftId, RepositoryCallback<Shift> callback) {
        executor.execute(() -> {
            try {
                Shift shift = shiftDao.getShiftById(shiftId);
                shift.setEndTime(System.currentTimeMillis());
                shift.setStatus("CLOSED");
                shiftDao.update(shift);
                callback.onSuccess(shift);
            } catch (Exception e) {
                callback.onError(e);
            }
        });
    }
    
    // Additional methods...
}

// Callback interface
public interface RepositoryCallback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}
```

---

## USE CASES (Domain Layer)

### StartShiftUseCase
```java
public class StartShiftUseCase {
    private final ShiftRepository repository;
    
    public StartShiftUseCase(ShiftRepository repository) {
        this.repository = repository;
    }
    
    public void execute(String bartenderId, UseCaseCallback<Shift> callback) {
        // Check if there's already an active shift
        repository.getActiveShift(new RepositoryCallback<Shift>() {
            @Override
            public void onSuccess(Shift activeShift) {
                if (activeShift != null) {
                    callback.onError(new IllegalStateException("Active shift already exists"));
                    return;
                }
                
                // Create new shift
                Shift newShift = new Shift();
                newShift.setId(UUID.randomUUID().toString());
                newShift.setBartenderId(bartenderId);
                newShift.setStartTime(System.currentTimeMillis());
                newShift.setStatus("ACTIVE");
                
                repository.startShift(newShift, new RepositoryCallback<Shift>() {
                    @Override
                    public void onSuccess(Shift shift) {
                        callback.onSuccess(shift);
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
            
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
```

### RecordCocktailUseCase
```java
public class RecordCocktailUseCase {
    private final ShiftRepository shiftRepository;
    private final InventoryRepository inventoryRepository;
    
    public RecordCocktailUseCase(ShiftRepository shiftRepository, InventoryRepository inventoryRepository) {
        this.shiftRepository = shiftRepository;
        this.inventoryRepository = inventoryRepository;
    }
    
    public void execute(String shiftId, String cocktailId, UseCaseCallback<Void> callback) {
        ShiftCocktail shiftCocktail = new ShiftCocktail();
        shiftCocktail.setId(UUID.randomUUID().toString());
        shiftCocktail.setShiftId(shiftId);
        shiftCocktail.setCocktailId(cocktailId);
        shiftCocktail.setTimestamp(System.currentTimeMillis());
        shiftCocktail.setQuantity(1);
        
        shiftRepository.recordCocktail(shiftCocktail, new RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Optionally: decrement inventory for cocktail ingredients
                // inventoryRepository.decrementIngredientsForCocktail(cocktailId);
                callback.onSuccess(null);
            }
            
            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
```

---

## UI SCREENS & VIEWMODELS

### 1. Shift Screen (Main Screen)

#### ShiftViewModel
```java
public class ShiftViewModel extends ViewModel {
    private final MutableLiveData<Shift> currentShift = new MutableLiveData<>();
    private final MutableLiveData<List<CocktailWithCount>> cocktailCounts = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    
    private final StartShiftUseCase startShiftUseCase;
    private final EndShiftUseCase endShiftUseCase;
    private final RecordCocktailUseCase recordCocktailUseCase;
    private final GetActiveShiftUseCase getActiveShiftUseCase;
    
    // Constructor with use cases injection
    
    public void startShift(String bartenderId) {
        isLoading.setValue(true);
        startShiftUseCase.execute(bartenderId, new UseCaseCallback<Shift>() {
            @Override
            public void onSuccess(Shift shift) {
                currentShift.postValue(shift);
                isLoading.postValue(false);
            }
            
            @Override
            public void onError(Exception e) {
                errorMessage.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }
    
    public void recordCocktail(String cocktailId) {
        Shift shift = currentShift.getValue();
        if (shift == null) return;
        
        recordCocktailUseCase.execute(shift.getId(), cocktailId, new UseCaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Refresh cocktail counts
                refreshCocktailCounts();
            }
            
            @Override
            public void onError(Exception e) {
                errorMessage.postValue("Error recording cocktail: " + e.getMessage());
            }
        });
    }
    
    public void endShift() {
        Shift shift = currentShift.getValue();
        if (shift == null) return;
        
        isLoading.setValue(true);
        endShiftUseCase.execute(shift.getId(), new UseCaseCallback<Shift>() {
            @Override
            public void onSuccess(Shift closedShift) {
                currentShift.postValue(null);
                isLoading.postValue(false);
                // Navigate to summary screen
            }
            
            @Override
            public void onError(Exception e) {
                errorMessage.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }
    
    // Getters for LiveData
    public LiveData<Shift> getCurrentShift() { return currentShift; }
    public LiveData<List<CocktailWithCount>> getCocktailCounts() { return cocktailCounts; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
}
```

#### ShiftActivity Layout (activity_shift.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <!-- App Bar -->
    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:title="Current Shift" />
    </com.google.android.material.appbar.AppBarLayout>
    
    <!-- Main Content -->
    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">
        
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">
            
            <!-- Shift Info Card -->
            <com.google.android.material.card.MaterialCardView
                android:id="@+id/shiftInfoCard"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp"
                app:cardElevation="2dp">
                
                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="vertical"
                    android:padding="16dp">
                    
                    <TextView
                        android:id="@+id/shiftStartTime"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="Shift started: --:--"
                        android:textSize="16sp" />
                    
                    <TextView
                        android:id="@+id/shiftDuration"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="8dp"
                        android:text="Duration: 0h 0m"
                        android:textSize="14sp"
                        android:textColor="?android:attr/textColorSecondary" />
                    
                    <TextView
                        android:id="@+id/totalCocktails"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="8dp"
                        android:text="Total cocktails: 0"
                        android:textSize="18sp"
                        android:textStyle="bold" />
                </LinearLayout>
            </com.google.android.material.card.MaterialCardView>
            
            <!-- Cocktail Grid -->
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Quick Access"
                android:textSize="18sp"
                android:textStyle="bold"
                android:layout_marginBottom="8dp" />
            
            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/cocktailGrid"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
                app:spanCount="2" />
        </LinearLayout>
    </androidx.core.widget.NestedScrollView>
    
    <!-- FAB for search/add cocktail -->
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAddCocktail"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        app:srcCompat="@android:drawable/ic_input_add" />
    
    <!-- Bottom Navigation -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNav"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        app:menu="@menu/bottom_nav_menu" />
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### CocktailAdapter (RecyclerView)
```java
public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.ViewHolder> {
    private List<CocktailWithCount> cocktails = new ArrayList<>();
    private OnCocktailClickListener listener;
    
    public interface OnCocktailClickListener {
        void onCocktailClick(Cocktail cocktail);
        void onCocktailLongClick(Cocktail cocktail);
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_cocktail_card, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CocktailWithCount item = cocktails.get(position);
        holder.bind(item, listener);
    }
    
    @Override
    public int getItemCount() {
        return cocktails.size();
    }
    
    public void setCocktails(List<CocktailWithCount> cocktails) {
        this.cocktails = cocktails;
        notifyDataSetChanged();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView countText;
        MaterialCardView cardView;
        
        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cocktailName);
            countText = itemView.findViewById(R.id.cocktailCount);
            cardView = itemView.findViewById(R.id.cocktailCard);
        }
        
        void bind(CocktailWithCount item, OnCocktailClickListener listener) {
            nameText.setText(item.getCocktail().getName());
            countText.setText(String.valueOf(item.getCount()));
            
            cardView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCocktailClick(item.getCocktail());
                }
            });
            
            cardView.setOnLongClickListener(v -> {
                if (listener != null) {
                    listener.onCocktailLongClick(item.getCocktail());
                }
                return true;
            });
        }
    }
}
```

---

## DEVELOPMENT PHASES

### PHASE 1: MVP Core (Priority: CRITICAL)

**Goal**: Basic shift tracking functionality working

**Tasks**:
1. **Project Setup**
   - Create new Android project with Java
   - Add Room, Lifecycle, Material Design dependencies
   - Setup package structure

2. **Database Layer**
   - Implement entities: Shift, Cocktail, ShiftCocktail
   - Create DAOs for basic operations
   - Setup AppDatabase with seed data callback
   - Create seed data for 15-20 common cocktails

3. **Repository Layer**
   - Implement ShiftRepository
   - Implement CocktailRepository
   - Create callback interfaces

4. **Use Cases**
   - StartShiftUseCase
   - EndShiftUseCase
   - RecordCocktailUseCase
   - GetActiveShiftUseCase

5. **UI - Shift Screen**
   - Create ShiftActivity with ViewBinding
   - Implement ShiftViewModel
   - Create cocktail grid layout (RecyclerView)
   - Implement tap to record cocktail
   - Display shift info (time, duration, total)
   - Add "Start Shift" / "End Shift" functionality

6. **UI - Shift Summary Screen**
   - Create SummaryActivity
   - Show total cocktails, duration, breakdown by type
   - Option to add notes
   - Close shift confirmation

**Deliverables**:
- User can start a shift
- User can quickly record cocktails (1-tap)
- User can end shift and see summary
- Data persists across app restarts

**Testing**:
- Manual testing of full shift workflow
- Test with 50+ cocktail entries in single shift
- Verify database persistence

---

### PHASE 2: Inventory Management (Priority: HIGH)

**Goal**: Track ingredient stock levels with alerts

**Tasks**:
1. **Database Expansion**
   - Add Ingredient entity
   - Add CocktailIngredient join table
   - Seed ingredient data
   - Link cocktails to ingredients

2. **Repository Layer**
   - InventoryRepository implementation
   - CRUD operations for ingredients
   - Low stock queries

3. **Use Cases**
   - GetAllIngredientsUseCase
   - UpdateIngredientStockUseCase
   - GetLowStockIngredientsUseCase
   - DecrementStockForCocktailUseCase (optional auto-decrement)

4. **UI - Inventory Screen**
   - Create InventoryActivity
   - List all ingredients with stock levels
   - Color-coded indicators (green/yellow/red)
   - Edit stock quantity dialog
   - Filter/search ingredients

5. **Notifications**
   - Low stock notification system
   - Daily inventory check reminder (optional)

**Deliverables**:
- Full ingredient management
- Visual stock level indicators
- Low stock alerts
- Manual stock updates

---

### PHASE 3: History & Analytics (Priority: MEDIUM)

**Goal**: View past shifts and basic statistics

**Tasks**:
1. **Use Cases**
   - GetShiftHistoryUseCase
   - GetShiftStatisticsUseCase
   - GetTopCocktailsUseCase

2. **UI - History Screen**
   - List past shifts with summary
   - Click to view detailed shift
   - Date range filter
   - Search functionality

3. **UI - Statistics Screen**
   - Total shifts count
   - Total cocktails served
   - Average cocktails per shift
   - Top 10 most popular cocktails
   - Charts (optional: using MPAndroidChart)

**Deliverables**:
- Browsable shift history
- Basic analytics dashboard

---

### PHASE 4: Multi-User Support (Priority: MEDIUM)

**Goal**: Support multiple bartenders on same device

**Tasks**:
1. **Database**
   - Bartender entity already defined
   - Seed default bartender

2. **Use Cases**
   - CreateBartenderUseCase
   - GetAllBartendersUseCase
   - SelectBartenderUseCase

3. **UI Changes**
   - Bartender selection screen on app launch
   - Profile management screen
   - Filter history by bartender

4. **Architecture**
   - Add BartenderSession singleton to track current user
   - Pass bartenderId to all shift operations

**Deliverables**:
- Multiple bartender profiles
- Shift attribution to specific bartender
- Per-bartender statistics

---

### PHASE 5: Cloud Sync & Backup (Priority: LOW - Future)

**Goal**: Enable data synchronization across devices

**Tasks**:
1. **Firebase Setup**
   - Add Firebase dependencies
   - Configure Firestore
   - Setup Firebase Authentication (simple)

2. **Sync Logic**
   - Repository pattern extended with remote data source
   - Offline-first sync strategy
   - Conflict resolution (last-write-wins)

3. **Background Sync**
   - WorkManager for periodic sync
   - Sync only on WiFi (optional)

**Deliverables**:
- Cloud backup of all data
- Multi-device support
- Shared inventory across team

---

## UI/UX REQUIREMENTS

### Design Principles
1. **Speed First**: Every interaction must be instantaneous (<200ms feedback)
2. **Large Touch Targets**: Minimum 48dp for tap areas (bar environment)
3. **High Contrast**: Easy to read in various lighting conditions
4. **Minimal Nesting**: Maximum 2 taps to reach any function
5. **Error Prevention**: Confirm destructive actions only

### Color Scheme
- **Primary**: Material Blue (#2196F3) or custom bar theme
- **Secondary**: Amber (#FFC107) for alerts/warnings
- **Success**: Green (#4CAF50) for stock OK
- **Warning**: Orange (#FF9800) for low stock
- **Error**: Red (#F44336) for critical stock
- **Background**: White (#FFFFFF) or dark theme option

### Typography
- **Headers**: 18-24sp, bold
- **Body**: 14-16sp, regular
- **Counters**: 20-28sp, bold (highly visible)
- **Font**: Roboto (system default)

### Animations
- Ripple effects on all tap actions
- Smooth transitions between screens (300ms)
- Counter increment animation (spring animation)
- Haptic feedback on cocktail recorded

---

## PERFORMANCE REQUIREMENTS

1. **Database Query Time**: < 50ms for single shift data
2. **UI Response**: < 100ms from tap to visual feedback
3. **App Launch**: < 2 seconds cold start
4. **Memory Usage**: < 50MB during active shift
5. **Battery**: Minimal drain (no background services in MVP)

---

## ERROR HANDLING

### Critical Errors
- Database corruption → Show error, offer data export/backup
- No active shift → Prompt to start shift before recording
- Duplicate shift start → Inform user of existing active shift

### Recoverable Errors
- Failed cocktail record → Retry mechanism + queue
- Network unavailable (Phase 5) → Offline mode message
- Low storage → Warn user, suggest data cleanup

### User Feedback
- Toast messages for quick actions
- Snackbar with undo for reversible actions
- Dialogs for important confirmations
- Progress bars for long operations (>1 second)

---

## TESTING STRATEGY

### Unit Tests
- Repository layer (mock DAOs)
- Use Cases (mock repositories)
- ViewModel logic (mock use cases)

### Instrumentation Tests
- Database CRUD operations
- DAO queries correctness
- UI interactions (Espresso)

### Manual Testing Scenarios
1. Complete shift workflow (start → record 30 cocktails → end)
2. App kill and restart during active shift
3. Rapid cocktail recording (stress test)
4. Inventory stock updates and alerts
5. Multi-day usage with history browsing

---

## SECURITY & PRIVACY

- **No user authentication** in MVP (single device, trusted environment)
- **Local data only** (no network transmission in Phase 1-4)
- **No permissions required** except storage (for backup)
- **Data export** option (JSON/CSV) for user control

---

## DEPENDENCIES & BUILD CONFIGURATION

### build.gradle (Project level)
```groovy
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.2.0'
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```

### build.gradle (App level)
```groovy
plugins {
    id 'com.android.application'
}

android {
    namespace 'it.faustobe.jigger'
    compileSdk 34
    
    defaultConfig {
        applicationId "it.faustobe.jigger"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
        
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        
        // Room schema export
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += ["room.schemaLocation": "$projectDir/schemas".toString()]
            }
        }
    }
    
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    
    buildFeatures {
        viewBinding true
    }
}

dependencies {
    // Core Android
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // Lifecycle & ViewModel
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-livedata:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-runtime:2.7.0'
    
    // Room Database
    implementation 'androidx.room:room-runtime:2.6.1'
    annotationProcessor 'androidx.room:room-compiler:2.6.1'
    
    // RecyclerView
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    
    // CardView
    implementation 'androidx.cardview:cardview:1.0.0'
    
    // WorkManager (for Phase 5)
    // implementation 'androidx.work:work-runtime:2.9.0'
    
    // Firebase (for Phase 5)
    // implementation platform('com.google.firebase:firebase-bom:32.7.0')
    // implementation 'com.google.firebase:firebase-firestore'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

---

## DELIVERABLES CHECKLIST

### Phase 1 Complete When:
- [ ] User can start a shift
- [ ] User can record cocktails with single tap
- [ ] Cocktail counter increments correctly
- [ ] Shift duration displayed and updates
- [ ] User can end shift
- [ ] Summary screen shows shift details
- [ ] Data persists after app restart
- [ ] No crashes during normal usage

### Phase 2 Complete When:
- [ ] All ingredients listed with stock levels
- [ ] Color indicators work (green/yellow/red)
- [ ] User can update ingredient quantities
- [ ] Low stock ingredients highlighted
- [ ] Navigation to inventory from main screen

### Phase 3 Complete When:
- [ ] History screen shows past shifts
- [ ] User can view detailed shift info
- [ ] Statistics screen displays metrics
- [ ] Date filtering works

### Phase 4 Complete When:
- [ ] Multiple bartender profiles can be created
- [ ] User selects bartender at shift start
- [ ] Shifts attributed to correct bartender
- [ ] Per-bartender statistics visible

---

## NOTES FOR CLAUDE CODE

1. **Code Style**: Use Java 8+ features where appropriate (lambdas, streams), but maintain Android compatibility (minSdk 24)

2. **Naming Conventions**:
   - Classes: PascalCase
   - Methods/Variables: camelCase
   - Constants: UPPER_SNAKE_CASE
   - XML IDs: snake_case

3. **Architecture**: Strictly follow MVVM + Repository pattern for maintainability and testability

4. **Comments**: Add JavaDoc for public methods, inline comments for complex logic

5. **Resource Organization**:
   - Strings in strings.xml (support localization)
   - Dimensions in dimens.xml
   - Colors in colors.xml
   - Styles in themes.xml

6. **Git Commits**: Logical commits per feature with clear messages

7. **Performance**: Use background threads for all database operations, never block UI thread

8. **Null Safety**: Check for null before using objects, use @Nullable/@NonNull annotations

9. **Memory Leaks**: Be careful with Context references in ViewModels, use Application context when possible

10. **Testing**: Write at least one unit test per use case to verify logic

---

## GETTING STARTED WITH CLAUDE CODE

### Recommended First Command:
```bash
claude-code "Read JIGGER_APP_SPEC.md and create the Android project structure for Jigger app. Start with Phase 1: implement the project setup, Room database with entities (Shift, Cocktail, ShiftCocktail), DAOs, and seed the initial cocktail data. Use package it.faustobe.jigger and follow all specifications exactly."
```

### Development Workflow:
1. **Phase 1**: Project setup + Database layer + Seed data
2. **Phase 1**: Repository layer + Use Cases
3. **Phase 1**: ShiftActivity UI + ViewModel
4. **Phase 1**: Testing and refinement
5. **Phase 2**: Inventory system (after Phase 1 is solid)

### Key Files to Generate First:
1. `build.gradle` files (project and app level)
2. `AndroidManifest.xml`
3. Entity classes in `data/local/entities/`
4. DAO interfaces in `data/local/dao/`
5. `AppDatabase.java` with seed data
6. Repository implementations
7. Use Case classes
8. ViewModels
9. Activity layouts and Java files

### Testing Strategy:
- Run the app after each major component
- Test database operations in isolation first
- Verify seed data loads correctly
- Test full shift workflow manually

---

## PROJECT METADATA

**Project Name:** Jigger  
**Package:** it.faustobe.jigger  
**Version:** 1.0.0  
**Min Android:** 7.0 (API 24)  
**Target Android:** 14 (API 34)  
**Language:** Java 8+  
**Developer:** Fausto Behrens  

**Specification Version:** 1.0  
**Last Updated:** 2026-01-19  
**Status:** Ready for Development  

---

END OF SPECIFICATION DOCUMENT
