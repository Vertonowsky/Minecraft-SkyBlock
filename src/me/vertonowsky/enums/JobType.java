package me.vertonowsky.enums;

public enum JobType {


    MINER,
    LUMBERJACK,
    ANGLER,
    WARRIOR;

    private static JobType type;



    public static void setState(JobType jobType) {
        JobType.type = jobType;
    }



    public static boolean isState(JobType jobType) {
        return JobType.type == jobType;
    }



    public static JobType getState() {
        return type;
    }



}
