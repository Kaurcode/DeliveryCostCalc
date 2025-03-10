package com.fujitsu.deliverycostcalc;

import java.util.Map;

public enum Phenomenon {
    STORMY,  // Glaze, hail or thunder
    SNOWY,  // Snow or sleet
    RAINY,  // Rain
    OTHER;  // Everything else

    private static final String[] STORMY_KEYWORDS = new String[]{"glaze", "hail", "thunder"};
    private static final String[] SNOWY_KEYWORDS = new String[]{"snow", "sleet"};
    private static final String[] RAINY_KEYWORDS = new String[]{"rain"};


    private static final Map<Phenomenon, String[]> PhenomenonToKeywordsMap = Map.of(
            STORMY, STORMY_KEYWORDS,
            SNOWY, SNOWY_KEYWORDS,
            RAINY, RAINY_KEYWORDS
    );

    public static Phenomenon parsePhenomenon(String phenomenon) {
        if (phenomenon == null || phenomenon.isEmpty()) {
            return OTHER;
        }

        phenomenon = phenomenon.toLowerCase();

        for (Map.Entry<Phenomenon, String[]> entry : PhenomenonToKeywordsMap.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (phenomenon.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }

        return Phenomenon.OTHER;
    }
}
