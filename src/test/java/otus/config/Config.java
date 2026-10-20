package otus.config;

public final  class Config {

    private void Config() {}

    // ---------- Профиль нагрузки ----------
    public static final double STEP           = 0.2;   // 10% от MAX
    public static final int    STEPS          = 10;    // 10 ступеней: 10%...100%
    public static final int    LEVEL_DURATION = 300;   // 5 минут на ступень
    public static final int    RAMP_DURATION  = 30;    // 30 секунд ramp

    public static final double MAX_RPS = 1.83;
    public static final double STABILITY_RPS = MAX_RPS * 0.8;
    public static final int STABILITY_DURATION = 3600;
    public static final int STABILITY_RAMP = 300;
}
