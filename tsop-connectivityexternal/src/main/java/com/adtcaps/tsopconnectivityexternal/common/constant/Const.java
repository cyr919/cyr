package com.adtcaps.tsopconnectivityexternal.common.constant;

public class Const {
	
	public static class Common {

        // 공통 Y,N 벨류
        public static final String VALUE_Y = "Y";
        public static final String VALUE_N = "N";
        public static final String VALUE_ALL = "ALL";

        // 기본 페이지 사이즈
        public static final int VALUE_DEFUALT_PAGE_SIZE = 10;

        // 통신 결과 코드
        public static class HTTP_RESPONSE_CODE {
            public static final String SUCCESS = "200";
            public static final String INVALID_REQUEST = "400";
            public static final String UNAUTORIZED = "401";
            public static final String REQUEST_TIMEOUT = "408";
            public static final String SERVER_ERROR = "500";
            public static final String FAIL = "-1";
        }
        // RESEULT CODE
        public static class RESULT_CODE {
            public static final String SUCCESS = "C0000";
            public static final String FAIL = "C9999";
            public static final String NOT_LOGIN = "NOT_LOGIN";
            public static final String UNAUTORIZED = "UNAUTORIZED";
            public static final String SERVER_ERROR = "Server Error";
        }


        // 콤마
        public static final String COMMA = ",";

        // 언어
        public static final String KOREAN = "kr";
        public static final String ENGLSH = "en";

        // 엑셀뷰
        public static final String VIEW_TYPE_EXCEL = "excelXlsxView";
    }

}
