package org.example.models.enums;

public enum Vitamins {
    A("Vitamin A (retinol, retinoid acid) is a nutrient important to vision, growth, cell division, reproduction and immunity. Vitamin A also has antioxidant properties."),
    B1("Thiamine plays a vital role in the growth and function of various cells. Only small amounts are stored in the liver, so a daily intake of thiamine-rich foods is needed."),
    B2("Vitamin B2, also called riboflavin, is one of 8 B vitamins. All B vitamins help the body to convert food (carbohydrates) into fuel (glucose), which is used to produce energy."),
    B3("Vitamin B3 is also known as niacin and has 2 other forms. Niacin helps the body make various sex and stress-related hormones in the adrenal glands and other parts of the body. Niacin helps improve circulation, and it has been shown to suppress inflammation."),
    B5("In addition to playing a role in the breakdown of fats and carbohydrates for energy, vitamin B5 is critical to the manufacture of red blood cells, as well as sex and stress-related hormones produced in the adrenal glands, small glands that sit atop the kidneys. Vitamin B5 is also important in maintaining a healthy digestive tract, and it helps the body use other vitamins, particularly B2 (also called riboflavin). It is sometimes called the 'anti-stressed' vitamin, but there is no concrete evidence whether it helps the body withstand stress."),
    B6("Vitamin B-6 is important for normal brain development and for keeping the nervous system and immune system healthy. Food sources of vitamin B-6 include poultry, fish, potatoes, chickpeas, bananas and fortified cereals."),
    B9("Folic acid is crucial for proper brain function and plays an important role in mental and emotional health. It aids in the production of DNA and RNA, the body's genetic material, and is especially important when cells and tissues are growing rapidly, such as in infancy, adolescence, and pregnancy. Folic acid also works closely with vitamin B12 to help make red blood cells and help iron work properly in the body."),
    B12("Vitamin B12, also known as cobalamin, is a water-soluble vitamin involved in metabolism. It is required by animals, which use it as a cofactor in DNA synthesis, and in both fatty acid and amino acid metabolism.[3] It is important in the normal functioning of the nervous system via its role in the synthesis of myelin, and in the circulatory system in the maturation of red blood cells in the bone marrow.[2][4] Plants do not need cobalamin and carry out the reactions with enzymes that are not dependent on it."),
    C("Vitamin C, also known as ascorbic acid, has several important functions. These include: helping to protect cells and keeping them healthy. maintaining healthy skin, blood vessels, bones and cartilage."),
    D("Vitamin D helps regulate the amount of calcium and phosphate in the body. These nutrients are needed to keep bones, teeth and muscles healthy."),
    E("Vitamin E helps maintain healthy skin and eyes, and strengthen the body's natural defence against illness and infection (the immune system)."),
    K("Vitamin K is a group of vitamins that the body needs for blood clotting, helping wounds to heal.");

    private final String description;

    Vitamins(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
