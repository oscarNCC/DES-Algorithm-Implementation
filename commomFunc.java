import java.math.BigInteger;

public class commomFunc {

        final static int[] IP = {
                        58, 50, 42, 34, 26, 18, 10, 2,
                        60, 52, 44, 36, 28, 20, 12, 4,
                        62, 54, 46, 38, 30, 22, 14, 6,
                        64, 56, 48, 40, 32, 24, 16, 8,
                        57, 49, 41, 33, 25, 17, 9, 1,
                        59, 51, 43, 35, 27, 19, 11, 3,
                        61, 53, 45, 37, 29, 21, 13, 5,
                        63, 55, 47, 39, 31, 23, 15, 7
        };
        final static int[] PC1 = {
                        57, 49, 41, 33, 25, 17, 9,
                        1, 58, 50, 42, 34, 26, 18,
                        10, 2, 59, 51, 43, 35, 27,
                        19, 11, 3, 60, 52, 44, 36,
                        63, 55, 47, 39, 31, 23, 15,
                        7, 62, 54, 46, 38, 30, 22,
                        14, 6, 61, 53, 45, 37, 29,
                        21, 13, 5, 28, 20, 12, 4
        };
        final static int[] PC2 = {
                        14, 17, 11, 24, 1, 5,
                        3, 28, 15, 6, 21, 10,
                        23, 19, 12, 4, 26, 8,
                        16, 7, 27, 20, 13, 2,
                        41, 52, 31, 37, 47, 55,
                        30, 40, 51, 45, 33, 48,
                        44, 49, 39, 56, 34, 53,
                        46, 42, 50, 36, 29, 32
        };
        final static int[] FP = {
                        40, 8, 48, 16, 56, 24, 64, 32,
                        39, 7, 47, 15, 55, 23, 63, 31,
                        38, 6, 46, 14, 54, 22, 62, 30,
                        37, 5, 45, 13, 53, 21, 61, 29,
                        36, 4, 44, 12, 52, 20, 60, 28,
                        35, 3, 43, 11, 51, 19, 59, 27,
                        34, 2, 42, 10, 50, 18, 58, 26,
                        33, 1, 41, 9, 49, 17, 57, 25
        };
        final static int[] P = {
                        16, 7, 20, 21,
                        29, 12, 28, 17,
                        1, 15, 23, 26,
                        5, 18, 31, 10,
                        2, 8, 24, 14,
                        32, 27, 3, 9,
                        19, 13, 30, 6,
                        22, 11, 4, 25
        };
        final static int[] E = {
                        32, 1, 2, 3, 4, 5,
                        4, 5, 6, 7, 8, 9,
                        8, 9, 10, 11, 12, 13,
                        12, 13, 14, 15, 16, 17,
                        16, 17, 18, 19, 20, 21,
                        20, 21, 22, 23, 24, 25,
                        24, 25, 26, 27, 28, 29,
                        28, 29, 30, 31, 32, 1
        };
        final static int[][][] S_BOXES = { {
                        { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                        { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                        { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                        { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
        }, {
                        { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                        { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                        { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                        { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
        }, {
                        { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                        { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                        { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                        { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
        }, {
                        { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                        { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                        { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                        { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
        }, {
                        { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                        { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                        { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                        { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
        }, {
                        { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                        { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                        { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                        { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }
        }, {
                        { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                        { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                        { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                        { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }
        }, {
                        { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                        { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                        { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                        { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }
        } };

        public static int[] leftRotate(int[] data, int s) {
                int dataLength = data.length;
                int[] ans = new int[dataLength]; // Assuming the original data length is 28 as per your example
                // Copy the part of the array from s to the end into the beginning of the ans
                // array
                System.arraycopy(data, s, ans, 0, dataLength - s);
                // Copy the first s elements of the data array into the end of the ans array
                System.arraycopy(data, 0, ans, dataLength - s, s);
                return ans;
        }

        public static String binaryToHex(String binary) {
                StringBuilder hex = new StringBuilder();
                int len = binary.length();
                int decimal;

                // Pad the binary string to make its length a multiple of 4
                int paddingLength = (4 - len % 4) % 4; // Calculate padding length
                String paddedBinary = "0".repeat(paddingLength) + binary; // Pad with leading zeros

                // Process 4 bits at a time
                for (int i = 0; i < paddedBinary.length(); i += 4) {
                        decimal = Integer.parseInt(paddedBinary.substring(i, i + 4), 2);
                        hex.append(Integer.toHexString(decimal).toUpperCase());
                }

                return hex.toString();
        }

        // public static String hexToBinary(String hex) {
        // try {
        // // Convert hex to a decimal integer
        // int decimal = Integer.parseInt(hex, 16);
        // // Convert the decimal to a binary string
        // String binary = Integer.toBinaryString(decimal);
        // // Ensure the binary string has the correct length (4 bits for each hex
        // // character)
        // while (binary.length() < hex.length() * 4) {
        // binary = "0" + binary;
        // }
        // return binary;
        // } catch (NumberFormatException e) {
        // // Handle the case where the input is not a valid hexadecimal string
        // return "Invalid Hexadecimal String";
        // }
        // }
        public static String hexToBinary(String hex) {
                try {
                        // Use BigInteger to handle large hexadecimal strings
                        String binary = new BigInteger(hex, 16).toString(2);
                        // Ensure the binary string has the correct length (4 bits for each hex
                        // character)
                        int minLength = hex.length() * 4;
                        while (binary.length() < minLength) {
                                binary = "0" + binary;
                        }
                        return binary;
                } catch (NumberFormatException e) {
                        // Ideally, throw a custom exception or use a different error handling strategy
                        throw new IllegalArgumentException("Invalid Hexadecimal String: " + hex);
                }
        }

        // public static String hexToBinary(String hex) {
        // StringBuilder binary = new StringBuilder();
        // for (int i = 0; i < hex.length(); i++) {
        // // Extract each hex character
        // int decimal = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
        // // Convert individual hex character to binary and pad with leading zeros
        // String binarySegment = String.format("%4s",
        // Integer.toBinaryString(decimal)).replace(' ', '0');
        // binary.append(binarySegment);
        // }
        // return binary.toString();
        // }

        public static String decimalToBinary(int dec) {
                return String.format("%4s", Integer.toBinaryString(dec)).replace(' ', '0');
        }

        public static int binaryToDecimal(String binaryString) {

                int decimal = Integer.parseInt(binaryString, 2);
                return decimal;
        }

        public static String sBox(String KER) {
                // S-box configuration: a 3D array representing substitution boxes

                StringBuilder sBoxResult = new StringBuilder();
                for (int i = 0; i < 48; i += 6) {
                        String sixBitSegment = KER.substring(i, i + 6);
                        String rowBits = sixBitSegment.charAt(0) + "" + sixBitSegment.charAt(5);
                        String columnBits = sixBitSegment.substring(1, 5);

                        int sBoxIndex = i / 6; // Determines which S-box to use based on the iteration
                        int row = binaryToDecimal(rowBits);
                        int column = binaryToDecimal(columnBits);
                        int sBoxValue = S_BOXES[sBoxIndex][row][column];

                        sBoxResult.append(decimalToBinary(sBoxValue));
                }

                return sBoxResult.toString();
        }

}
