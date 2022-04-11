package com.sky.live.cam.opengl;

public class Constants {

    public Constants() {
    }

    public static enum GestureEventCode {
        TAP(0),
        PAN(1),
        ROTATE(2),
        SCALE(3),
        LONG_PRESS(4),
        DOUBLE_CLICK(5);

        private final int code;

        private GestureEventCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    public static enum TouchEventCode {
        BEGAN(0),
        MOVED(1),
        STATIONARY(2),
        ENDED(3),
        CANCELLED(4);

        private final int code;

        private TouchEventCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    public static enum ImageQualityAsfSceneMode {
        ASF_SCENE_MODE_LIVE_GAME(0),
        ASF_SCENE_MODE_LIVE_PEOPLE(1),
        ASF_SCENE_MODE_LIVE_EDIT(2),
        ASF_SCENE_MODE_LIVE_RECORED_MAIN(3),
        ASF_SCENE_MODE_LIVE_RECORED_FRONT(4);

        private final int mode;

        private ImageQualityAsfSceneMode(int mode) {
            this.mode = mode;
        }

        public int getMode() {
            return this.mode;
        }
    }

    public static enum ImageQulityPowerLevel {
        POWER_LEVEL_DEFAULT(0),
        POWER_LEVEL_LOW(1),
        POWER_LEVEL_NORMAL(2),
        POWER_LEVEL_HIGH(3),
        POWER_LEVEL_AUTO(4);

        private final int level;

        private ImageQulityPowerLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }
    }

    public static enum ImageQulityBackendType {
        IMAGE_QUALITY_BACKEND_GPU(0);

        private final int type;

        private ImageQulityBackendType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }

    public static enum FaceMaskType {
        FACE_MASK_FACE(3),
        FACE_MASK_TEETH(2),
        FACE_MASK_MOUTH(1);

        private int value;

        private FaceMaskType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum ImageQualityType {
        IMAGE_QUALITY_TYPE_NONE,
        IMAGE_QUALITY_TYPE_VIDEO_SR,
        IMAGE_QUALITY_TYPE_NIGHT_SCENE,
        IMAGE_QUALITY_TYPE_ADAPTIVE_SHARPEN;

        private ImageQualityType() {
        }
    }

    public static enum StudentIdOcrModelType {
        BEF_STUDENT_ID_OCR_MODEL(1);

        private int value;

        private StudentIdOcrModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum CarParamType {
        BEF_Car_Detect(1),
        BEF_Brand_Rec(2);

        private int value;

        private CarParamType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum CarModelType {
        DetectModel(1),
        BrandNodel(2),
        OCRModel(3),
        TrackModel(4);

        private int value;

        private CarModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum GazeEstimationModelType {
        BEF_GAZE_ESTIMATION_MODEL1(1);

        private int value;

        private GazeEstimationModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum GazeEstimationParamType {
        BEF_GAZE_ESTIMATION_EDGE_MODE(1),
        BEF_GAZE_ESTIMATION_CAMERA_FOV(2),
        BEF_GAZE_ESTIMATION_DIVERGENCE(3);

        private int value;

        public int getValue() {
            return this.value;
        }

        private GazeEstimationParamType(int value) {
            this.value = value;
        }
    }

    public static enum VideoClsParamType {
        BEF_AI_kVideoClsEdgeMode(1);

        private int value;

        public int getValue() {
            return this.value;
        }

        private VideoClsParamType(int value) {
            this.value = value;
        }
    }

    public static enum VideoClsModelType {
        BEF_AI_kVideoClsModel1(1);

        private int value;

        public int getValue() {
            return this.value;
        }

        private VideoClsModelType(int value) {
            this.value = value;
        }
    }

    public static enum C2ParamType {
        BEF_AI_C2_USE_VIDEO_MODE(0),
        BEF_AI_C2_USE_MultiLabels(1);

        private int value;

        public int getValue() {
            return this.value;
        }

        private C2ParamType(int value) {
            this.value = value;
        }
    }

    public static enum C2ModelType {
        BEF_AI_kC2Model1(1);

        private int value;

        public int getValue() {
            return this.value;
        }

        private C2ModelType(int value) {
            this.value = value;
        }
    }

    public static enum C1ParamType {
        BEF_AI_C1_USE_VIDEO_MODE(1),
        BEF_AI_C1_USE_MultiLabels(2);

        private int value;

        private C1ParamType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum C1ModelType {
        BEF_AI_C1_MODEL_SMALL(1),
        BEF_AI_C1_MODEL_LARGE(2);

        private int value;

        private C1ModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum DynamicActionParamType {
        BEF_AI_DYNAMIC_ACTION_REFRESH_FRAME_INTERVAL(1),
        BEF_AI_DYNAMIC_ACTION_MAX_PERSON_NUM(2);

        private int value;

        private DynamicActionParamType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum DynamicActionModelType {
        BEF_AI_DYNAMIC_ACTION_MODEL_SK(1),
        BEF_AI_DYNAMIC_ACTION_MODEL_DETECT(2),
        BEF_AI_DYNAMIC_ACTION_MODEL_DYNAMIC_ACTION(4);

        private int value;

        private DynamicActionModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum GeneralObjectParamType {
        BEF_AI_GENERAL_OBJECT_DETECT_SHORT_SIDE_LEN(1);

        private int value;

        private GeneralObjectParamType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum GeneralObjectModelType {
        BEF_AI_GENERAL_OBJECT_PURE_DETECT(1),
        BEF_AI_GENERAL_OBJECT_DETECT_CLS(2),
        BEF_AI_GENERAL_OBJECT_CLS_NAME(3),
        BEF_AI_GENERAL_OBJECT_DETECT_TRACK(4);

        private int value;

        private GeneralObjectModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum HumanDistanceModelType {
        BEF_HUMAN_DISTANCE_MODEL1(1);

        private int value;

        private HumanDistanceModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum LightClsType {
        None(-1),
        Indoor_Yellow(0),
        Indoor_White(1),
        Indoor_weak(2),
        Sunny(3),
        Cloudy(4),
        Night(5),
        Backlight(6);

        private int value;

        private LightClsType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class PetFaceAction {
        public static final int BEF_LEFT_EYE_PET_FACE = 1;
        public static final int BEF_RIGHT_EYE_PET_FACE = 2;
        public static final int BEF_MOUTH_PET_FACE = 4;

        public PetFaceAction() {
        }
    }

    public static class PetFaceDetectType {
        public static final int BEF_PET_FACE_CAT = 1;
        public static final int BEF_PET_FACE_DOG = 2;
        public static final int BEF_PET_FACE_HUMAN = 3;
        public static final int BEF_PET_FACE_OTHER = 99;

        public PetFaceDetectType() {
        }
    }

    public static class PetFaceDetectConfig {
        public static final int BEF_PET_FACE_DETECT_CAT = 1;
        public static final int BEF_PET_FACE_DETECT_DOG = 2;
        public static final int BEF_PET_FACE_DETECT_QUICK = 4;

        public PetFaceDetectConfig() {
        }
    }

    public static enum HumanDistanceParamType {
        BEF_HumanDistanceEdgeMode(0),
        BEF_HumanDistanceCameraFov(1);

        private int value;

        private HumanDistanceParamType(int v) {
            this.value = v;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum HeadSegmentParamType {
        BEF_AI_HS_ENABLE_TRACKING(1),
        BEF_AI_HS_MAX_FACE(2);

        private int value;

        private HeadSegmentParamType(int v) {
            this.value = v;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum PorraitMattingParamType {
        BEF_MP_EdgeMode(0),
        BEF_MP_FrashEvery(1),
        BEF_MP_OutputMinSideLen(2),
        BEF_MP_VIDEO_MODE(5);

        private int value;

        private PorraitMattingParamType(int v) {
            this.value = v;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum PortraitMatting {
        BEF_PORTAITMATTING_LARGE_MODEL(0),
        BEF_PORTAITMATTING_SMALL_MODEL(1);

        private int value;

        private PortraitMatting(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum HandParamType {
        BEF_HAND_MAX_HAND_NUM(2),
        BEF_HAND_DETECT_MIN_SIDE(3),
        BEF_HAND_CLS_SMOOTH_FACTOR(4),
        BEF_HAND_USE_ACTION_SMOOTH(5),
        BEF_HAND_ALGO_LOW_POWER_MODE(6),
        BEF_HAND_ALGO_AUTO_MODE(7),
        BEF_HAND_ALGO_TIME_ELAPSED_THRESHOLD(8),
        BEF_HAND_ALGO_MAX_TEST_FRAME(9),
        BEF_HAND_IS_USE_DOUBLE_GESTURE(10),
        BEF_HNAD_ENLARGE_FACTOR_REG(11),
        BEF_HAND_NARUTO_GESTUER(12);

        private int value;

        private HandParamType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class FaceExpression {
        public static final int BEF_FACE_ATTRIBUTE_ANGRY = 0;
        public static final int BEF_FACE_ATTRIBUTE_DISGUST = 1;
        public static final int BEF_FACE_ATTRIBUTE_FEAR = 2;
        public static final int BEF_FACE_ATTRIBUTE_HAPPY = 3;
        public static final int BEF_FACE_ATTRIBUTE_SAD = 4;
        public static final int BEF_FACE_ATTRIBUTE_SURPRISE = 5;
        public static final int BEF_FACE_ATTRIBUTE_NEUTRAL = 6;
        public static final int BEF_FACE_ATTRIBUTE_NUM_EXPRESSION = 7;

        public FaceExpression() {
        }
    }

    public static class FaceRacial {
        public static final int BEF_FACE_ATTRIBUTE_WHITE = 0;
        public static final int BEF_FACE_ATTRIBUTE_YELLOW = 1;
        public static final int BEF_FACE_ATTRIBUTE_INDIAN = 2;
        public static final int BEF_FACE_ATTRIBUTE_BLACK = 3;
        public static final int BEF_FACE_ATTRIBUTE_NUM_RACIAL = 4;

        public FaceRacial() {
        }
    }

    public static class FaceAttribute {
        public static final int BEF_FACE_ATTRIBUTE_AGE = 1;
        public static final int BEF_FACE_ATTRIBUTE_GENDER = 2;
        public static final int BEF_FACE_ATTRIBUTE_EXPRESSION = 4;
        public static final int BEF_FACE_ATTRIBUTE_ATTRACTIVE = 8;
        public static final int BEF_FACE_ATTRIBUTE_HAPPINESS = 16;
        public static final int BEF_FACE_ATTRIBUTE_CONFUSE = 1024;

        public FaceAttribute() {
        }
    }

    public static enum HandModelType {
        BEF_HAND_MODEL_DETECT(1),
        BEF_HAND_MODEL_BOX_REG(2),
        BEF_HAND_MODEL_GESTURE_CLS(4),
        BEF_HAND_MODEL_KEY_POINT(8);

        private int value;

        private HandModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public class FaceSegmentType {
        public static final int BEF_FACE_MOUTH_MASK = 1;
        public static final int BEF_FACE_TEETH_MASK = 2;
        public static final int BEF_FACE_FACE_MASK = 3;

        public FaceSegmentType() {
        }
    }

    public class FaceSegmentConfig {
        public static final int BEF_MOBILE_FACE_MOUTH_MASK = 768;
        public static final int BEF_MOBILE_FACE_TEETH_MASK = 768;
        public static final int BEFF_MOBILE_FACE_REST_MASK = 1280;

        public FaceSegmentConfig() {
        }
    }

    public class FaceExtraModel {
        public static final int BEF_MOBILE_FACE_240_DETECT = 256;
        public static final int BEF_MOBILE_FACE_280_DETECT = 2304;
        public static final int BEF_MOBILE_FACE_240_DETECT_FASTMODE = 3145728;

        public FaceExtraModel() {
        }
    }

    public static class FaceAction {
        public static final int BEF_FACE_DETECT = 1;
        public static final int BEF_EYE_BLINK = 2;
        public static final int BEF_MOUTH_AH = 4;
        public static final int BEF_HEAD_SHAKE = 8;
        public static final int BEF_HEAD_NOD = 16;
        public static final int BEF_BROW_RAISE = 32;
        public static final int BEF_MOUTH_POUT = 64;
        public static final int BEF_DETECT_FULL = 127;

        public FaceAction() {
        }
    }

    public static class FaceDetectType {
        public static final int BEF_FACE_PARAM_FACE_DETECT_INTERVAL = 1;
        public static final int BEF_FACE_PARAM_MAX_FACE_NUM = 2;
        public static final int BEF_FACE_PARAM_MIN_DETECT_LEVEL = 3;
        public static final int BEF_FACE_PARAM_BASE_SMOOTH_LEVEL = 4;
        public static final int BEF_FACE_PARAM_EXTRA_SMOOTH_LEVEL = 5;
        public static final int BEF_FACE_PARAM_MASK_SMOOTH_TYPE = 6;

        public FaceDetectType() {
        }
    }

    public static class DetectMode {
        public static final int BEF_DETECT_MODE_VIDEO = 131072;
        public static final int BEF_DETECT_MODE_VIDEO_SLOW = 65536;
        public static final int BEF_DETECT_MODE_IMAGE = 262144;
        public static final int BEF_DETECT_MODE_IMAGE_SLOW = 524288;

        public DetectMode() {
        }
    }

    public static class BytedResultCode {
        public static final int BEF_RESULT_SUC = 0;
        public static final int BEF_RESULT_FAIL = -1;
        public static final int BEF_RESULT_FILE_NOT_FIND = -2;
        public static final int BEF_RESULT_FAIL_DATA_ERROR = -3;
        public static final int BEF_RESULT_INVALID_HANDLE = -4;
        public static final int BEF_RESULT_INVALID_LICENSE = -114;
        public static final int BEF_RESULT_INVALID_IMAGE_FORMAT = -7;
        public static final int BEF_RESULT_MODEL_LOAD_FAILURE = -8;

        public BytedResultCode() {
        }
    }

    public static enum IntensityType {
        Filter(12),
        BeautyWhite(1),
        BeautySmooth(2),
        FaceReshape(3),
        BeautySharp(9),
        MakeUpLip(17),
        MakeUpBlusher(18);

        private int id;

        public int getId() {
            return this.id;
        }

        private IntensityType(int id) {
            this.id = id;
        }
    }

    public static enum Rotation {
        CLOCKWISE_ROTATE_0(0),
        CLOCKWISE_ROTATE_90(1),
        CLOCKWISE_ROTATE_180(2),
        CLOCKWISE_ROTATE_270(3);

        public int id = 0;

        private Rotation(int id) {
            this.id = id;
        }
    }

    public static enum TextureFormat {
        Texure2D(3553),
        Texture_Oes(36197);

        private int value;

        private TextureFormat(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum RenderAPI {
        GLES20(0),
        GLES30(1);

        private int value;

        private RenderAPI(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum PixlFormat {
        RGBA8888(0),
        BGRA8888(1),
        BGR888(2),
        RGB888(3),
        BEF_AI_PIX_FMT_YUV420P(5),
        BEF_AI_PIX_FMT_NV12(6),
        BEF_AI_PIX_FMT_NV21(7);

        private int value;

        private PixlFormat(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

