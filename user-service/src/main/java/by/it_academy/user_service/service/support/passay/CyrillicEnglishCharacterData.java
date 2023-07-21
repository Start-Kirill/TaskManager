package by.it_academy.user_service.service.support.passay;

import org.passay.CharacterData;

public enum CyrillicEnglishCharacterData implements CharacterData {

    /**
     * Lower case characters.
     */
    LowerCase("INSUFFICIENT_LOWERCASE", "абвгдеёжзийклмнопрстуфхцчшщъыьэюяіabcdefghijklmnopqrstuvwxyz"),

    /**
     * Upper case characters.
     */
    UpperCase("INSUFFICIENT_UPPERCASE", "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯІABCDEFGHIJKLMNOPQRSTUVWXYZ");

    /**
     * Error code.
     */
    private final String errorCode;

    /**
     * Characters.
     */
    private final String characters;

    /**
     * Creates a new cyrillic character data.
     *
     * @param code       Error code
     * @param charString Characters as string
     */
    CyrillicEnglishCharacterData(final String code, final String charString) {
        errorCode = code;
        characters = charString;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getCharacters() {
        return characters;
    }
}
