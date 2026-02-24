package it.faustobe.jigger.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import it.faustobe.jigger.data.local.entities.Cocktail;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CocktailDao_Impl implements CocktailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Cocktail> __insertionAdapterOfCocktail;

  private final EntityDeletionOrUpdateAdapter<Cocktail> __updateAdapterOfCocktail;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public CocktailDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCocktail = new EntityInsertionAdapter<Cocktail>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cocktails` (`id`,`name`,`category`,`glassType`,`imageUrl`,`isCustom`,`isFavorite`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Cocktail entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategory());
        }
        if (entity.getGlassType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGlassType());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImageUrl());
        }
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
      }
    };
    this.__updateAdapterOfCocktail = new EntityDeletionOrUpdateAdapter<Cocktail>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `cocktails` SET `id` = ?,`name` = ?,`category` = ?,`glassType` = ?,`imageUrl` = ?,`isCustom` = ?,`isFavorite` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Cocktail entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getCategory());
        }
        if (entity.getGlassType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGlassType());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImageUrl());
        }
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        if (entity.getId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM cocktails WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Cocktail cocktail) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCocktail.insert(cocktail);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Cocktail> cocktails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCocktail.insert(cocktails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Cocktail cocktail) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCocktail.handle(cocktail);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final String cocktailId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    if (cocktailId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, cocktailId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public List<Cocktail> getAllCocktails() {
    final String _sql = "SELECT * FROM cocktails ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Cocktail> _result = new ArrayList<Cocktail>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Cocktail _item;
        _item = new Cocktail();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpGlassType;
        if (_cursor.isNull(_cursorIndexOfGlassType)) {
          _tmpGlassType = null;
        } else {
          _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
        }
        _item.setGlassType(_tmpGlassType);
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _item.setImageUrl(_tmpImageUrl);
        final boolean _tmpIsCustom;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
        _tmpIsCustom = _tmp != 0;
        _item.setCustom(_tmpIsCustom);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _item.setFavorite(_tmpIsFavorite);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Cocktail>> getAllCocktailsLive() {
    final String _sql = "SELECT * FROM cocktails ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cocktails"}, false, new Callable<List<Cocktail>>() {
      @Override
      @Nullable
      public List<Cocktail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<Cocktail> _result = new ArrayList<Cocktail>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Cocktail _item;
            _item = new Cocktail();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpGlassType;
            if (_cursor.isNull(_cursorIndexOfGlassType)) {
              _tmpGlassType = null;
            } else {
              _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
            }
            _item.setGlassType(_tmpGlassType);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            _item.setImageUrl(_tmpImageUrl);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item.setCustom(_tmpIsCustom);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            _item.setFavorite(_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Cocktail> getFavoriteCocktails() {
    final String _sql = "SELECT * FROM cocktails WHERE isFavorite = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Cocktail> _result = new ArrayList<Cocktail>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Cocktail _item;
        _item = new Cocktail();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpGlassType;
        if (_cursor.isNull(_cursorIndexOfGlassType)) {
          _tmpGlassType = null;
        } else {
          _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
        }
        _item.setGlassType(_tmpGlassType);
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _item.setImageUrl(_tmpImageUrl);
        final boolean _tmpIsCustom;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
        _tmpIsCustom = _tmp != 0;
        _item.setCustom(_tmpIsCustom);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _item.setFavorite(_tmpIsFavorite);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Cocktail>> getFavoriteCocktailsLive() {
    final String _sql = "SELECT * FROM cocktails WHERE isFavorite = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"cocktails"}, false, new Callable<List<Cocktail>>() {
      @Override
      @Nullable
      public List<Cocktail> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<Cocktail> _result = new ArrayList<Cocktail>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Cocktail _item;
            _item = new Cocktail();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpGlassType;
            if (_cursor.isNull(_cursorIndexOfGlassType)) {
              _tmpGlassType = null;
            } else {
              _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
            }
            _item.setGlassType(_tmpGlassType);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            _item.setImageUrl(_tmpImageUrl);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item.setCustom(_tmpIsCustom);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            _item.setFavorite(_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Cocktail> searchCocktails(final String query) {
    final String _sql = "SELECT * FROM cocktails WHERE name LIKE '%' || ? || '%' ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Cocktail> _result = new ArrayList<Cocktail>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Cocktail _item;
        _item = new Cocktail();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpGlassType;
        if (_cursor.isNull(_cursorIndexOfGlassType)) {
          _tmpGlassType = null;
        } else {
          _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
        }
        _item.setGlassType(_tmpGlassType);
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _item.setImageUrl(_tmpImageUrl);
        final boolean _tmpIsCustom;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
        _tmpIsCustom = _tmp != 0;
        _item.setCustom(_tmpIsCustom);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _item.setFavorite(_tmpIsFavorite);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Cocktail getCocktailById(final String cocktailId) {
    final String _sql = "SELECT * FROM cocktails WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (cocktailId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, cocktailId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final Cocktail _result;
      if (_cursor.moveToFirst()) {
        _result = new Cocktail();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _result.setCategory(_tmpCategory);
        final String _tmpGlassType;
        if (_cursor.isNull(_cursorIndexOfGlassType)) {
          _tmpGlassType = null;
        } else {
          _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
        }
        _result.setGlassType(_tmpGlassType);
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _result.setImageUrl(_tmpImageUrl);
        final boolean _tmpIsCustom;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
        _tmpIsCustom = _tmp != 0;
        _result.setCustom(_tmpIsCustom);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _result.setFavorite(_tmpIsFavorite);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Cocktail> getCocktailsByCategory(final String category) {
    final String _sql = "SELECT * FROM cocktails WHERE category = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfGlassType = CursorUtil.getColumnIndexOrThrow(_cursor, "glassType");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<Cocktail> _result = new ArrayList<Cocktail>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Cocktail _item;
        _item = new Cocktail();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpGlassType;
        if (_cursor.isNull(_cursorIndexOfGlassType)) {
          _tmpGlassType = null;
        } else {
          _tmpGlassType = _cursor.getString(_cursorIndexOfGlassType);
        }
        _item.setGlassType(_tmpGlassType);
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        _item.setImageUrl(_tmpImageUrl);
        final boolean _tmpIsCustom;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
        _tmpIsCustom = _tmp != 0;
        _item.setCustom(_tmpIsCustom);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _item.setFavorite(_tmpIsFavorite);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCocktailCount() {
    final String _sql = "SELECT COUNT(*) FROM cocktails";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
