package com.jayasundara.aifarm;


public class PrinterCommands {

    public static byte[] INIT = {27, 64};
    public static byte[] RESET = {24};
    public static final byte[] AUTO_POWER_OFF = {27,77,55,54,53,52,48,13};
    public static byte[] SELECT_FONT_A = {27, 33, 0};
    public static byte[] SELECT_FONT_12 = {27, 75, 49, 13};
    public static byte[] SELECT_FONT_25 = {27, 75, 49, 49, 13};
    public static byte[] FONT_BOLD_ON = {27, 85, 49};
    public static byte[] FONT_BOLD_OFF = {27, 85, 48};
    public static byte[] FONT_UNDERLINE_ON = {27, 85, 85};
    public static byte[] FONT_UNDERLINE_OFF = {27, 85, 117};
    public static byte[] FONT_HI_ON = {28};
    public static byte[] FONT_HI_OFF = {29};
    public static byte[] FONT_WIDE_ON = {14};
    public static byte[] FONT_WIDE_OFF = {15};
    public static byte[] CHAR_SET = {27, 70, 49};
    public static byte[] PRINT_LEFT = {27, 70, 76};
    public static byte[] PRINT_RIGHT = {27, 70, 86};
    public static byte[] SET_BAR_CODE_HEIGHT = {29, 104, 100};
    public static byte[] PRINT_BAR_CODE_1 = {29, 107, 2};
    public static byte[] SEND_NULL_BYTE = {0x00};
    public static byte[] SELECT_PRINT_SHEET = {0x1B, 0x63, 0x30, 0x02};
    public static byte[] FEED_PAPER_AND_CUT = {0x1D, 0x56, 66, 0x00};
    public static byte[] SELECT_CYRILLIC_CHARACTER_CODE_TABLE = {0x1B, 0x74, 0x11};


    public static final byte HT = 0x9;
    public static final byte LF = 0x0A;
    public static final byte CR = 0x0D;
    public static final byte ESC = 0x1B;
    public static final byte DLE = 0x10;
    public static final byte GS = 0x1D;
    public static final byte FS = 0x1C;
    public static final byte STX = 0x02;
    public static final byte US = 0x1F;
    public static final byte CAN = 0x18;
    public static final byte CLR = 0x0C;
    public static final byte EOT = 0x04;

    public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33, -128, 0};
    public static byte[] SET_LINE_SPACING_24 = {0x1B, 0x33, 24};
    public static byte[] SET_LINE_SPACING_30 = {0x1B, 0x33, 30};

    public static byte[] TRANSMIT_DLE_PRINTER_STATUS = {0x10, 0x04, 0x01};
    public static byte[] TRANSMIT_DLE_OFFLINE_PRINTER_STATUS = {0x10, 0x04, 0x02};
    public static byte[] TRANSMIT_DLE_ERROR_STATUS = {0x10, 0x04, 0x03};
    public static byte[] TRANSMIT_DLE_ROLL_PAPER_SENSOR_STATUS = {0x10, 0x04, 0x04};

    public static final byte[] ESC_FONT_COLOR_DEFAULT = new byte[] { 0x1B, 'r',0x00 };
    public static final byte[] FS_FONT_ALIGN = new byte[] { 0x1C, 0x21, 1, 0x1B,
            0x21, 1 };
    public static final byte[] ESC_ALIGN_LEFT = new byte[] { 0x1b, 'a', 0x00 };
    public static final byte[] ESC_ALIGN_RIGHT = new byte[] { 0x1b, 'a', 0x02 };
    public static final byte[] ESC_ALIGN_CENTER = new byte[] { 0x1b, 'a', 0x01 };
    public static final byte[] ESC_SET_BOLD = new byte[] { ESC, 0x45, 0x01 };
    public static final byte[] ESC_CANCEL_BOLD = new byte[] { 0x1B, 0x45, 0 };
    public static final byte[] ESC_CANCEL_UNDERLINE = new byte[] { FS,0x2D,0x00 };
    public static final byte[] ESC_SET_UNDERLINE = new byte[] {FS,0x2D,0x01 };


    /*********************************************/
    public static final byte[] ESC_HORIZONTAL_CENTERS = new byte[] { 0x1B, 0x44, 20, 28, 00};
    public static final byte[] ESC_CANCLE_HORIZONTAL_CENTERS = new byte[] { 0x1B, 0x44, 00 };
    /*********************************************/

    public static final byte[] ESC_ENTER = new byte[] { 0x1B, 0x4A, 0x40 };
    public static final byte[] PRINTE_TEST = new byte[] { 0x1D, 0x28, 0x41 };

    public static final byte[] DEVICE_INFO = new byte[] {0x1B, 0x2B };
    public static final byte[] ESC_RESETPRINTER = new byte[] { ESC,0x40};


    public static final byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
    //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
    public static final byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
    public static final byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
    public static final byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
}
