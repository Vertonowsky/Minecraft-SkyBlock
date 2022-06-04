package me.vertonowsky.enums;

public enum QuestState {


    ZABLOKOWANE,
    W_TRAKCIE,
    WYKONANE,
    ZAKONCZONE;

    private static QuestState type;



    public static void setState(QuestState questState) {
        QuestState.type = questState;
    }



    public static boolean isState(QuestState questState) {
        return QuestState.type == questState;
    }



    public static QuestState getState() {
        return type;
    }



}
