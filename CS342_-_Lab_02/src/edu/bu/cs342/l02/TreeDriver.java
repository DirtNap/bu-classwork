package edu.bu.cs342.l02;

public class TreeDriver {

    public static void main(String[] args) {
        TreeDriver go = new TreeDriver();
        go.input();

    }

    public void input() {
        BinaryTree btree = BinaryTree.create();
        btree.insert("Blueberry");
        btree.insert("A&W Cream Soda");
        btree.insert("A&W Root Beer");
        btree.insert("Chocolate Pudding");
        btree.insert("Dr Pepper");
        btree.insert("Cantaloupe");
        btree.insert("Coconut");
        btree.insert("Juicy Pear");
        btree.insert("Chili Mango");
        btree.insert("Berry Blue");
        btree.insert("Licorice");
        btree.insert("Green Apple");
        btree.insert("Kiwi");
        btree.insert("Cappucino");
        btree.insert("Cotton Candy");
        btree.insert("Cinnamon");
        btree.insert("Caramel Corn");
        btree.insert("Island Punch");
        btree.insert("Mango");
        btree.insert("Lemon Lime");
        btree.insert("Crushed Pineapple");
        btree.insert("Lemon Drop");
        btree.insert("Green Apple");
        btree.insert("French Vanilla");
        btree.insert("Toasted Marshmallow");
        btree.insert("Sunkist Lemon");
        btree.insert("Strawberry Daquiri");
        btree.insert("Red Apple");
        btree.insert("Pomegranate");
        btree.insert("Mixed Berry Smoothie");
        btree.insert("Pina Colada");
        btree.insert("Sizzling Cinnamon");
        btree.insert("Plum");
        btree.insert("Orange Sherbet");
        btree.insert("Sunkist Pink Grapefruit");
        btree.insert("Strawberry Cheesecake");
        btree.insert("Sunkist Lime");
        btree.insert("Sour Cherry");
        btree.insert("Peach");
        btree.insert("Margarita");
        btree.insert("Strawberry Jame");
        btree.insert("Wild Blackberry");
        btree.insert("Very Cherry");
        btree.insert("Tutti Fruitti");
        btree.insert("Top Banana");
        btree.insert("Watermelon");

        System.out.println("Printing unsorted tree:");

        btree.print();

        btree.delete("Blueberry");

        System.out.println("Printing sorted tree:");

        btree.printSort();
        btree.find("Cotton Candy");

        btree.delete("Peach");
        btree.delete("vomit");

        btree.find("Margarita");

    }

}
