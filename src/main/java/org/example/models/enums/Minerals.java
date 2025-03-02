package org.example.models.enums;

public enum Minerals {
    CALCIUM("Calcium helps build bones and keep teeth healthy, regulates muscle contractions, including your heartbeat and makes sure blood clots normally"),
    IODINE("Iodine helps make thyroid hormones, which help keep cells and the metabolic rate (the speed at which chemical reactions take place in the body) healthy."),
    IRON("Iron is important in making red blood cells, which carry oxygen around the body."),
    SODIUM("Sodium chloride is commonly known as salt. Sodium and chloride are minerals needed by the body in small amounts to help keep the level of fluids in the body balanced. Chloride also helps the body digest food."),
    PHOSPHORUS("Phosphorus is a mineral that helps build strong bones and teeth, and helps release energy from food."),
    POTASSIUM("Potassium is a mineral that helps control the balance of fluids in the body, and also helps the heart muscle work properly."),
    MAGNESIUM("Magnesium is a mineral that helps turn the food we eat into energy and make sure the parathyroid glands, which produce hormones important for bone health, work normally"),
    ZINC("Zinc helps with making new cells and enzymes, processing carbohydrate, fat and protein in food and wound healing"),
    SELENIUM("Selenium helps the immune system work properly, as well as in reproduction. It also helps prevent damage to cells and tissues."),
    MANGANESE("Manganese helps make and activate some of the enzymes in the body. Enzymes are proteins that help the body carry out chemical reactions, such as breaking down food.");

    private final String description;

    Minerals(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    }
