package leltek.viewer.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Probe interface
 */
public interface Probe {

    /**
     * 回傳目前的版本
     * @return version 版本
     */
    String getVersion();

    /**
     * SystemListener
     */
    interface SystemListener {

        /**
         * 初始成功
         */
        void onInitialized();

        /**
         * 初始失敗
         */
        void onInitializationError(String message);

        void onInitialingLowVoltageError(String message);

        /**
         * 系統錯誤
         */
        void onSystemError(String message);
    }

    /**
     * 設定InitializeListener
     *
     * @param systemListener systemListener
     */
    void setSystemListener(SystemListener systemListener);


    /**
     * 執行初始化：讀assets下cfg目錄的設定檔，目連結device。
     * SystemListener.onInitialized()表示初始成功
     * SystemListener.onInitializationError表示初始失敗
     */
    void initialize();

    /**
     * @return true 如果已經連上device
     */
    boolean isConnected();

    /**
     * @return true 如果已經正在向device下指令
     */
    boolean isRequesting();

    /**
     * BatteryListener
     */
    interface BatteryListener {

        /**
         * device battery level發生改變，可用來更新UI的battery level
         *
         * @param newBatteryLevel newBatteryLevel
         */
        void onBatteryLevelChanged(int newBatteryLevel);

        /**
         * battery level太低，可用來提醒user
         *
         * @param BatteryLevel BatteryLevel
         */
        void onBatteryLevelTooLow(int BatteryLevel);
    }

    /**
     * 設定BatteryListener
     *
     * @param batteryListener batteryListener
     */
    void setBatteryListener(BatteryListener batteryListener);

    /**
     * @return 0~100 (%), null代表尚未取得device的值
     */
    Integer getBatteryLevel();

    /**
     * TemperatureListener
     */
    interface TemperatureListener {

        /**
         * device溫度發生改變，可用來更新UI的temperature
         *
         * @param newTemperature newTemperature
         */
        void onTemperatureChanged(int newTemperature);

        /**
         * device溫度太高，可用來提醒user
         *
         * @param temperature temperature
         */
        void onTemperatureOverHeated(int temperature);
    }

    /**
     * 設定TemperatureListener
     *
     * @param temperatureListener temperatureListener
     */
    void setTemperatureListener(TemperatureListener temperatureListener);

    /**
     * @return 0~100 (°C), null代表尚未取得device的值
     */
    Integer getTemperature();

    /**
     * B Mode Frame Data
     */
    class BModeFrameData {
        public int gain;
        public int dr;
        public int grayMap;
        public int persistence;
        public int enhanceLevel;
    }

    /**
     * C Mode Frame Data
     */
    class CModeFrameData {
        public int colorGain;
        public int colorPersistence;
        public int colorPrf;
        public int colorThreshold;
        public EnumColorWall colorWall;
        public EnumColorAngle colorAngle;
        public float originXPx;
        public float originYPx;
        public float rPx;
    }

    /**
     * Frame class
     */
    abstract class Frame {

        public EnumMode mode;
        public Date date;
        public int frameRate;

        public float freq;
        public EnumDepth depth;

        public BModeFrameData bModeFrameData;

        public CModeFrameData cModeFrameData;

        public byte[] rawImage;

        public Frame(Probe probe, byte[] rawImage, EnumMode mode) {
            this.mode = mode;
            this.date = new Date();
            this.frameRate = probe.getFrameRate();
            this.freq = probe.getFreq();
            this.depth = probe.getDepth();
            this.bModeFrameData = new BModeFrameData();

            bModeFrameData.gain = probe.getGain();
            bModeFrameData.dr = probe.getDr();
            bModeFrameData.grayMap = probe.getGrayMap();
            bModeFrameData.persistence = probe.getPersistence();
            bModeFrameData.enhanceLevel = probe.getEnhanceLevel();

            if (this.mode == EnumMode.MODE_C) {
                this.cModeFrameData = new CModeFrameData();
                cModeFrameData.colorGain = probe.getColorGain();
                cModeFrameData.colorPersistence = probe.getColorPersistence();
                cModeFrameData.colorPrf = probe.getColorPrf();
                cModeFrameData.colorThreshold = probe.getColorThreshold();
                cModeFrameData.colorWall = probe.getColorWall();
                cModeFrameData.colorAngle = probe.getColorAngle();
                cModeFrameData.originXPx = probe.getOriginXPx();
                cModeFrameData.originYPx = probe.getOriginYPx();
                cModeFrameData.rPx = probe.getRPx();
            }

            this.rawImage = rawImage;
        }

        public abstract Bitmap getBitmap();
    }

    /**
     * CineBufferListener
     */
    interface CineBufferListener {

        /**
         * Cine buffer的個數增加，可用來更新UI的buffer個數
         *
         * @param newCineBufferCount newCineBufferCount
         */
        void onCineBufferCountIncreased(int newCineBufferCount);

        /**
         * Cine buffer的個數清除為0，可用來更新UI的buffer個數
         */
        void onCineBufferCleared();
    }

    /**
     * 設定CineBufferListener
     *
     * @param cineBufferListener cineBufferListener
     */
    void setCineBufferListener(CineBufferListener cineBufferListener);

    /**
     * 從CineBuffer取得frame
     *
     * @param index 從0開始
     * @return 只有在非live時才有值，live時會回傳null
     */
    Frame getFrameFromCineBuffer(int index);

    /**
     * ScanListener
     */
    interface ScanListener {

        /**
         * scan mode切換成功
         *
         * @param mode Mode_B表示切換到B mode, Mode_C表示切換到color mode
         */
        void onModeSwitched(EnumMode mode);

        /**
         * scan mode切換失敗
         */
        void onModeSwitchingError();

        /**
         * 跟device之間的connection發生error
         */
        void onConnectionError();

        /**
         * device開始scan
         */
        void onScanStarted();

        /**
         * device停止scan
         */
        void onScanStopped();

        /**
         * scan進行中，傳回接收到的frame
         *
         * @param frame 接收到的frame
         */
        void onNewFrameReady(Frame frame, Bitmap bitmap);

        /**
         * 設定depth成功
         *
         * @param newDepth newDepth
         */
        void onDepthSet(EnumDepth newDepth);

        /**
         * 設定depth失敗
         */
        void onDepthSetError();

        /**
         * 當發生此事件時，代表此硬體來不及做影像後處理，底層會將image丟掉
         */
        void onImageBufferOverflow();
    }

    /**
     * 設定ScanListener
     *
     * @param scanListener scanListener
     */
    void setScanListener(ScanListener scanListener);

    enum EnumMode {
        MODE_B,
        MODE_C
    }

    /**
     * 試圖將scan mode切換為B mode
     * 由ScanListener.onModeSwitched(EnumMode mode), 且mode等於MODE_B表示切換成功
     * 由ScanListener.onModeSwitchingError()表示切換失敗
     */
    void swithToBMode();

    /**
     * 試圖將scan mode切換為C mode
     * 由ScanListener.onModeSwitched(String mode), 且mode等於MODE_C表示切換成功
     * 由ScanListener.onModeSwitchingError()表示切換失敗
     */
    void swithToCMode();

    /**
     * 回傳目前的scan mode
     *
     * @return "B"表示B mode
     */
    EnumMode getMode();

    /**
     * 要求開始scan
     * 由ScanListener.onScanStarted()表示開始scan成功
     */
    void startScan();

    /**
     * 要求停止scan
     * 由ScanListener.onScanStopped()表示停止scan成功
     */
    void stopScan();

    /**
     * @return true 如果正在scan
     */
    boolean isLive();

    /**
     * 取得目前的frame rate, 單位為 Hz
     *
     * @return frame rate
     */
    int getFrameRate();

    /**
     * 單位為 MHz
     *
     * @return Freq Freq
     */
    float getFreq();

    enum EnumDepth {
        LinearDepth_32,
        LinearDepth_63,
        ConvexDepth_126,
        ConvexDepth_189
    }

    /**
     * 取得目前的depth, 單位為 mm
     *
     * @return depth
     */
    EnumDepth getDepth();

    /**
     * 要求設定depth
     * 由ScanListener.onDepthSet()表示設定成功
     * 由ScanListener.onDepthSetError表示設定失敗
     *
     * @param newDepth 合理值 LinearDepth_32, LinearDepth_63 (mm) for linear,
     *              ConvexDepth_126, ConvexDepth_189 (mm) for convex
     */
    void setDepth(EnumDepth newDepth);

    /**
     * 取得目前的gain
     *
     * @return gain
     */
    int getGain();

    /**
     * 設定gain
     *
     * @param gain 合理值 0 ~ 100
     */
    void setGain(int gain);

    /**
     * 取得目前的dynamic range
     *
     * @return dynamic range
     */
    int getDr();

    /**
     * 設定dynamic range
     *
     * @param dr 合理值 0 ~ 100
     */
    void setDr(int dr);

    /**
     * 取得目前的gray map編號
     *
     * @return gray map編號
     */
    int getGrayMap();

    /**
     * 取得最大的gray map編號
     *
     * @return 最大的gray map編號
     */
    int getGrayMapMaxValue();

    /**
     * 設定gray map
     *
     * @param grayMap 合理值 0 ~ GrayMapMaxValue
     */
    void setGrayMap(int grayMap);

    /**
     * 取得目前的persistence設定值
     *
     * @return persistence
     */
    int getPersistence();

    /**
     * 設定persistence
     *
     * @param persistence 合理值 0 ~ 4
     */
    void setPersistence(int persistence);

    /**
     * 取得目前的image enhance level
     *
     * @return image enhance level
     */
    int getEnhanceLevel();

    /**
     * 設定image enhance level
     *
     * @param enhanceLevel 合理值0 ~ 4
     */
    void setEnhanceLevel(int enhanceLevel);

    /**
     * 取得目前第一組TGC的值
     *
     * @return 目前第一組TGC的值
     */
    int getTgc1();

    /**
     * 設定第一組TGC
     *
     * @param tgc1 合理值0 ~ 100
     */
    void setTgc1(int tgc1);

    /**
     * 取得目前第二組TGC的值
     *
     * @return 目前第二組TGC的值
     */
    int getTgc2();

    /**
     * 設定第二組TGC
     *
     * @param tgc2 合理值0 ~ 100
     */
    void setTgc2(int tgc2);

    /**
     * 取得目前第三組TGC的值
     *
     * @return 目前第三組TGC的值
     */
    int getTgc3();

    /**
     * 設定第三組TGC
     *
     * @param tgc3 合理值0 ~ 100
     */
    void setTgc3(int tgc3);

    /**
     * 取得目前第四組TGC的值
     *
     * @return 目前第四組TGC的值
     */
    int getTgc4();

    /**
     * 設定第四組TGC
     *
     * @param tgc4 合理值0 ~ 100
     */
    void setTgc4(int tgc4);

    /**
     * 將全部的TGC設為50
     */
    void resetAllTgc();

    /**
     * 取得目前的color gain
     *
     * @return gain
     */
    int getColorGain();

    /**
     * 設定color gain
     *
     * @param colorGain 合理值 0 ~ 100
     */
    void setColorGain(int colorGain);

    /**
     * 取得目前的color persistence設定值
     *
     * @return color persistence
     */
    int getColorPersistence();

    /**
     * 設定persistence
     *
     * @param colorPersistence 合理值 0 ~ 4
     */
    void setColorPersistence(int colorPersistence);

    /**
     * 取得目前的color PRF設定值
     *
     * @return color PRF
     */
    int getColorPrf();

    /**
     * 設定color PRF
     *
     * @param colorPrf 合理值 2, 4, 5 (kHz)
     */
    void setColorPrf(int colorPrf);

    /**
     * 取得目前的color Threshold設定值
     *
     * @return color Threshold
     */
    int getColorThreshold();

    /**
     * 設定color Threshold
     *
     * @param colorThreshold 合理值 1~8
     */
    void setColorThreshold(int colorThreshold);

    /**
     * 定義Color Wall的合理值
     */
    enum EnumColorWall {
        ColorWall_14,
        ColorWall_19,
        ColorWall_24,
        ColorWall_34,
        ColorWall_49,
        ColorWall_53,
        ColorWall_73
    }

    /**
     * 取得目前的color Wall
     *
     * @return color Wall
     */
    EnumColorWall getColorWall();

    /**
     * 設定color Wall
     *
     * @param colorWall 合理值以EnumColorWall定義
     */
    void setColorWall(EnumColorWall colorWall);

    /**
     * 定義Color Angle的合理值
     */
    enum EnumColorAngle {
        ColorAngle_Minus6,
        ColorAngle_Minus3,
        ColorAngle_0,
        ColorAngle_3,
        ColorAngle_6
    }

    /**
     * 取得目前的color Angle
     *
     * @return color Angle
     */
    EnumColorAngle getColorAngle();

    /**
     * 設定color Angle
     *
     * @param colorAngle 合理值以EnumColorAngle定義
     */
    void setColorAngle(int colorAngle);

    /**
     * 回傳底層image bitmap的寬度
     *
     * @return image bitmap的寬度 (pixel)
     */
    int getImageWidthPx();

    /**
     * 回傳底層image bitmap的高度
     *
     * @return image bitmap的高度 (pixel)
     */
    int getImageHeightPx();

    class BModePreset {
        public EnumDepth depth;
        public int gain;
        public int dr;
        public int grayMap;
        public int persistence;
        public int enhanceLevel;

        public BModePreset(EnumDepth depth, int gain, int dr, int grayMap, int persistence, int enhanceLevel) {
            this.depth = depth;
            this.gain = gain;
            this.dr = dr;
            this.grayMap = grayMap;
            this.persistence = persistence;
            this.enhanceLevel = enhanceLevel;
        }
    }

    /**
     * 用BModePreset來設定系統的B mode參數
     *
     * @param preset preset
     */
    void setByBModePreset(BModePreset preset);

    /**
     * 取得convex半徑
     * @return r convex半徑 (pixel), 如果是linear則回傳0
     */
    float getRPx();

    /**
     * 取得convex角度
     * @return theta convex角度 (degree), 如果是linear則回傳0
     */
    float getTheta();

    /**
     * 取得convex 原點x軸坐標
     * @return originXPx convex 原點x軸坐標 (pixel), 如果是linear則回傳0
     */
    float getOriginXPx();

    /**
     * 取得convex 原點y軸坐標
     * @return originYPx convex 原點y軸坐標 (pixel), 如果是linear則回傳0
     */
    float getOriginYPx();

    /**
     * 設定linear ROI data
     */
    void setLinearRoiData(float roiXPx, float roiYPx, float roiX2Px, float roiY2Px);

    /**
     * 設定convex ROI data
     */
    void setConvexRoiData(float roiStartRPx, float roiEndRPx, float roiStartTheta, float roiEndTheta);
}
