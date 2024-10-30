package org.example.forum.constants;

public class AppConstants {

    // Post Interaction
    public static final int LIKE = 1;
    public static final int DISLIKE = 2;

    // Sorting Types
    public static final int SORT_BY_FIRST_ALPHABET_IN_TITLE = 1;
    public static final int SORT_BY_FAVOURITISM = 2;
    public static final int SORT_BY_TOTAL_INTERACTIONS = 3;

    // Account Status
    public static final int MUTED = 2;

    // Gender
    public static final int MALE = 1;

    public static final int FEMALE = 2;

    //SECRET KEY for login
    public static final String SECRET_KEY = "secretfortheproject123456789566343535353453890234567435554";

    //Annual deletion date
    public static final long DELETION_DATE = (8 * 60 * 60 * 1000) + (5 * 60 * 1000);

    private AppConstants() {}
}
