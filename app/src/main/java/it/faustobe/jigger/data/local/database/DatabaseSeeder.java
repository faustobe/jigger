package it.faustobe.jigger.data.local.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import it.faustobe.jigger.data.local.entities.Bartender;
import it.faustobe.jigger.data.local.entities.Cocktail;
import it.faustobe.jigger.data.local.entities.Ingredient;

public class DatabaseSeeder {

    private static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static List<Cocktail> getInitialCocktails() {
        List<Cocktail> cocktails = new ArrayList<>();

        // ============================================
        // IBA OFFICIAL COCKTAILS - THE UNFORGETTABLES
        // ============================================
        cocktails.add(new Cocktail(uuid(), "Alexander", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Americano", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Angel Face", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Aviation", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Between the Sheets", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Casino", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Clover Club", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Daiquiri", "IBA", "COUPE", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Dry Martini", "IBA", "MARTINI", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Gin Fizz", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Hanky Panky", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "John Collins", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Last Word", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Manhattan", "IBA", "COUPE", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Martinez", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Mary Pickford", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Monkey Gland", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Negroni", "IBA", "ROCKS", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Old Fashioned", "IBA", "ROCKS", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Paradise", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Planter's Punch", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Porto Flip", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Ramos Gin Fizz", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Rusty Nail", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Sazerac", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Sidecar", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Stinger", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Tuxedo", "IBA", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Whiskey Sour", "IBA", "ROCKS", null, false, true));
        cocktails.add(new Cocktail(uuid(), "White Lady", "IBA", "COUPE", null, false, false));

        // ============================================
        // IBA OFFICIAL COCKTAILS - CONTEMPORARY CLASSICS
        // ============================================
        cocktails.add(new Cocktail(uuid(), "Bellini", "IBA", "FLUTE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Black Russian", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Bloody Mary", "IBA", "HIGHBALL", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Caipirinha", "IBA", "ROCKS", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Champagne Cocktail", "IBA", "FLUTE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Corpse Reviver #2", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Cosmopolitan", "IBA", "MARTINI", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Cuba Libre", "IBA", "HIGHBALL", null, false, true));
        cocktails.add(new Cocktail(uuid(), "French 75", "IBA", "FLUTE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "French Connection", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "God Father", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "God Mother", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Golden Dream", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Grasshopper", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Harvey Wallbanger", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Hemingway Special", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Horse's Neck", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Irish Coffee", "IBA", "IRISH_MUG", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Kir", "IBA", "WINE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Long Island Iced Tea", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Mai Tai", "IBA", "ROCKS", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Margarita", "IBA", "COUPE", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Mimosa", "IBA", "FLUTE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Mint Julep", "IBA", "JULEP_CUP", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Mojito", "IBA", "HIGHBALL", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Moscow Mule", "IBA", "COPPER_MUG", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Piña Colada", "IBA", "HURRICANE", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Pisco Sour", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Sea Breeze", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Sex on the Beach", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Singapore Sling", "IBA", "HURRICANE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Tequila Sunrise", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "White Russian", "IBA", "ROCKS", null, false, false));

        // ============================================
        // IBA OFFICIAL COCKTAILS - NEW ERA DRINKS
        // ============================================
        cocktails.add(new Cocktail(uuid(), "Aperol Spritz", "IBA", "WINE", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Barracuda", "IBA", "FLUTE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Bramble", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Canchanchara", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Dark 'n' Stormy", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Espresso Martini", "IBA", "MARTINI", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Fernandito", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "French Martini", "IBA", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Illegal", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Lemon Drop Martini", "IBA", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Naked and Famous", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "New York Sour", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Old Cuban", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Paloma", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Paper Plane", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Penicillin", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Pornstar Martini", "IBA", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Russian Spring Punch", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Southside", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Spicy Fifty", "IBA", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Spritz Veneziano", "IBA", "WINE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Suffering Bastard", "IBA", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Tipperary", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Tommy's Margarita", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Trinidad Sour", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Ve.N" +
                ".To", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Vieux Carré", "IBA", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Yellow Bird", "IBA", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Zombie", "IBA", "HURRICANE", null, false, false));

        // ============================================
        // CLASSIC COCKTAILS (Non-IBA but popular)
        // ============================================
        cocktails.add(new Cocktail(uuid(), "Gin Tonic", "CLASSIC", "HIGHBALL", null, false, true));
        cocktails.add(new Cocktail(uuid(), "Vodka Tonic", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Vodka Soda", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Rum & Coke", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Whiskey & Coke", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Screwdriver", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Cape Cod", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Madras", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Bay Breeze", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Greyhound", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Salty Dog", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Gibson", "CLASSIC", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Dirty Martini", "CLASSIC", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Vesper", "CLASSIC", "MARTINI", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Rob Roy", "CLASSIC", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Blood and Sand", "CLASSIC", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Boulevardier", "CLASSIC", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Jungle Bird", "CLASSIC", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Amaretto Sour", "CLASSIC", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Midori Sour", "CLASSIC", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Kamikaze", "CLASSIC", "COUPE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "B-52", "CLASSIC", "SHOT", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Alabama Slammer", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Blue Lagoon", "CLASSIC", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Tequila Shot", "CLASSIC", "SHOT", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Jäger Bomb", "CLASSIC", "SHOT", null, false, false));

        // ============================================
        // TIKI & TROPICAL
        // ============================================
        cocktails.add(new Cocktail(uuid(), "Painkiller", "TIKI", "HURRICANE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Hurricane", "TIKI", "HURRICANE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Blue Hawaiian", "TIKI", "HURRICANE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Bahama Mama", "TIKI", "HURRICANE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Chi Chi", "TIKI", "HURRICANE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Scorpion", "TIKI", "SCORPION_BOWL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Navy Grog", "TIKI", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Jet Pilot", "TIKI", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Three Dots and a Dash", "TIKI", "TIKI_MUG", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Fog Cutter", "TIKI", "TIKI_MUG", null, false, false));

        // ============================================
        // ITALIAN APERITIVI
        // ============================================
        cocktails.add(new Cocktail(uuid(), "Negroni Sbagliato", "APERITIVO", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Sbagliato", "APERITIVO", "ROCKS", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Hugo", "APERITIVO", "WINE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Garibaldi", "APERITIVO", "HIGHBALL", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Sgroppino", "APERITIVO", "FLUTE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Limoncello Spritz", "APERITIVO", "WINE", null, false, false));
        cocktails.add(new Cocktail(uuid(), "Rossini", "APERITIVO", "FLUTE", null, false, false));

        return cocktails;
    }

    public static List<Ingredient> getInitialIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        // Spirits
        ingredients.add(new Ingredient(uuid(), "Gin", "SPIRIT", "ml", 2000, 500, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Vodka", "SPIRIT", "ml", 2000, 500, 0.015f));
        ingredients.add(new Ingredient(uuid(), "Rum Bianco", "SPIRIT", "ml", 1500, 400, 0.018f));
        ingredients.add(new Ingredient(uuid(), "Rum Scuro", "SPIRIT", "ml", 1000, 300, 0.022f));
        ingredients.add(new Ingredient(uuid(), "Tequila Blanco", "SPIRIT", "ml", 1200, 400, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Tequila Reposado", "SPIRIT", "ml", 1000, 300, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Mezcal", "SPIRIT", "ml", 700, 200, 0.04f));
        ingredients.add(new Ingredient(uuid(), "Whiskey Bourbon", "SPIRIT", "ml", 1500, 400, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Whiskey Rye", "SPIRIT", "ml", 1000, 300, 0.035f));
        ingredients.add(new Ingredient(uuid(), "Scotch Whisky", "SPIRIT", "ml", 1000, 300, 0.04f));
        ingredients.add(new Ingredient(uuid(), "Irish Whiskey", "SPIRIT", "ml", 1000, 300, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Cognac", "SPIRIT", "ml", 700, 200, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Brandy", "SPIRIT", "ml", 700, 200, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Cachaça", "SPIRIT", "ml", 700, 200, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Pisco", "SPIRIT", "ml", 700, 200, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Absinthe", "SPIRIT", "ml", 500, 100, 0.05f));

        // Liqueurs
        ingredients.add(new Ingredient(uuid(), "Campari", "LIQUEUR", "ml", 1000, 200, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Aperol", "LIQUEUR", "ml", 1000, 200, 0.012f));
        ingredients.add(new Ingredient(uuid(), "Vermouth Rosso", "LIQUEUR", "ml", 1000, 200, 0.015f));
        ingredients.add(new Ingredient(uuid(), "Vermouth Dry", "LIQUEUR", "ml", 1000, 200, 0.015f));
        ingredients.add(new Ingredient(uuid(), "Vermouth Bianco", "LIQUEUR", "ml", 750, 150, 0.015f));
        ingredients.add(new Ingredient(uuid(), "Triple Sec", "LIQUEUR", "ml", 750, 150, 0.018f));
        ingredients.add(new Ingredient(uuid(), "Cointreau", "LIQUEUR", "ml", 700, 150, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Grand Marnier", "LIQUEUR", "ml", 700, 150, 0.035f));
        ingredients.add(new Ingredient(uuid(), "Kahlua", "LIQUEUR", "ml", 700, 150, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Baileys", "LIQUEUR", "ml", 700, 150, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Amaretto", "LIQUEUR", "ml", 700, 150, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Frangelico", "LIQUEUR", "ml", 700, 150, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Maraschino", "LIQUEUR", "ml", 500, 100, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Chartreuse Verde", "LIQUEUR", "ml", 500, 100, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Chartreuse Gialla", "LIQUEUR", "ml", 500, 100, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Benedictine", "LIQUEUR", "ml", 500, 100, 0.04f));
        ingredients.add(new Ingredient(uuid(), "Drambuie", "LIQUEUR", "ml", 500, 100, 0.035f));
        ingredients.add(new Ingredient(uuid(), "Galliano", "LIQUEUR", "ml", 500, 100, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Sambuca", "LIQUEUR", "ml", 700, 150, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Limoncello", "LIQUEUR", "ml", 700, 150, 0.015f));
        ingredients.add(new Ingredient(uuid(), "Midori", "LIQUEUR", "ml", 500, 100, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Blue Curaçao", "LIQUEUR", "ml", 500, 100, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Crème de Cassis", "LIQUEUR", "ml", 500, 100, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Crème de Menthe", "LIQUEUR", "ml", 500, 100, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Crème de Cacao", "LIQUEUR", "ml", 500, 100, 0.02f));
        ingredients.add(new Ingredient(uuid(), "Fernet Branca", "LIQUEUR", "ml", 700, 150, 0.025f));
        ingredients.add(new Ingredient(uuid(), "Jägermeister", "LIQUEUR", "ml", 700, 150, 0.02f));
        ingredients.add(new Ingredient(uuid(), "St-Germain", "LIQUEUR", "ml", 500, 100, 0.04f));
        ingredients.add(new Ingredient(uuid(), "Peach Schnapps", "LIQUEUR", "ml", 700, 150, 0.015f));

        // Juices
        ingredients.add(new Ingredient(uuid(), "Succo di Limone", "JUICE", "ml", 2000, 500, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Succo di Lime", "JUICE", "ml", 1500, 400, 0.004f));
        ingredients.add(new Ingredient(uuid(), "Succo di Arancia", "JUICE", "ml", 2000, 500, 0.002f));
        ingredients.add(new Ingredient(uuid(), "Succo di Pompelmo", "JUICE", "ml", 1000, 300, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Succo di Ananas", "JUICE", "ml", 1500, 400, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Succo di Cranberry", "JUICE", "ml", 1000, 300, 0.004f));
        ingredients.add(new Ingredient(uuid(), "Succo di Pomodoro", "JUICE", "ml", 1000, 300, 0.002f));
        ingredients.add(new Ingredient(uuid(), "Succo di Mela", "JUICE", "ml", 1000, 300, 0.002f));
        ingredients.add(new Ingredient(uuid(), "Succo di Passion Fruit", "JUICE", "ml", 500, 100, 0.005f));

        // Syrups
        ingredients.add(new Ingredient(uuid(), "Sciroppo Zucchero", "SYRUP", "ml", 1000, 200, 0.002f));
        ingredients.add(new Ingredient(uuid(), "Sciroppo Agave", "SYRUP", "ml", 500, 100, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Grenadine", "SYRUP", "ml", 500, 100, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Sciroppo Miele", "SYRUP", "ml", 500, 100, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Sciroppo Orgeat", "SYRUP", "ml", 500, 100, 0.004f));
        ingredients.add(new Ingredient(uuid(), "Sciroppo Fiori Sambuco", "SYRUP", "ml", 500, 100, 0.004f));
        ingredients.add(new Ingredient(uuid(), "Falernum", "SYRUP", "ml", 500, 100, 0.004f));

        // Mixers
        ingredients.add(new Ingredient(uuid(), "Soda", "MIXER", "ml", 5000, 1000, 0.001f));
        ingredients.add(new Ingredient(uuid(), "Tonic Water", "MIXER", "ml", 3000, 750, 0.002f));
        ingredients.add(new Ingredient(uuid(), "Ginger Beer", "MIXER", "ml", 2000, 500, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Ginger Ale", "MIXER", "ml", 2000, 500, 0.002f));
        ingredients.add(new Ingredient(uuid(), "Cola", "MIXER", "ml", 3000, 750, 0.001f));
        ingredients.add(new Ingredient(uuid(), "Prosecco", "MIXER", "ml", 2000, 500, 0.01f));
        ingredients.add(new Ingredient(uuid(), "Champagne", "MIXER", "ml", 1500, 500, 0.03f));
        ingredients.add(new Ingredient(uuid(), "Espresso", "MIXER", "ml", 500, 100, 0.005f));
        ingredients.add(new Ingredient(uuid(), "Acqua di Cocco", "MIXER", "ml", 1000, 300, 0.003f));
        ingredients.add(new Ingredient(uuid(), "Crema di Cocco", "MIXER", "ml", 500, 100, 0.004f));
        ingredients.add(new Ingredient(uuid(), "Latte", "MIXER", "ml", 1000, 300, 0.001f));
        ingredients.add(new Ingredient(uuid(), "Panna", "MIXER", "ml", 500, 100, 0.003f));

        // Bitters
        ingredients.add(new Ingredient(uuid(), "Angostura Bitters", "BITTER", "ml", 200, 50, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Orange Bitters", "BITTER", "ml", 200, 50, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Peychaud's Bitters", "BITTER", "ml", 150, 30, 0.06f));

        // Garnishes
        ingredients.add(new Ingredient(uuid(), "Menta Fresca", "GARNISH", "bunch", 20, 5, 0.5f));
        ingredients.add(new Ingredient(uuid(), "Basilico", "GARNISH", "bunch", 15, 4, 0.5f));
        ingredients.add(new Ingredient(uuid(), "Lime", "GARNISH", "piece", 50, 10, 0.3f));
        ingredients.add(new Ingredient(uuid(), "Limone", "GARNISH", "piece", 50, 10, 0.25f));
        ingredients.add(new Ingredient(uuid(), "Arancia", "GARNISH", "piece", 30, 8, 0.4f));
        ingredients.add(new Ingredient(uuid(), "Pompelmo", "GARNISH", "piece", 20, 5, 0.5f));
        ingredients.add(new Ingredient(uuid(), "Cetriolo", "GARNISH", "piece", 20, 5, 0.3f));
        ingredients.add(new Ingredient(uuid(), "Olive", "GARNISH", "piece", 100, 20, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Cipollina", "GARNISH", "piece", 50, 10, 0.05f));
        ingredients.add(new Ingredient(uuid(), "Ciliegia Maraschino", "GARNISH", "piece", 50, 10, 0.1f));
        ingredients.add(new Ingredient(uuid(), "Sedano", "GARNISH", "piece", 20, 5, 0.3f));
        ingredients.add(new Ingredient(uuid(), "Noce Moscata", "GARNISH", "piece", 10, 2, 0.2f));

        return ingredients;
    }

    public static Bartender getDefaultBartender() {
        return new Bartender("default", "Bartender", System.currentTimeMillis());
    }
}
