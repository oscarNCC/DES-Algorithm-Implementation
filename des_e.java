import java.util.Arrays;

public class des_e {

    public String encrypt(String key, String data) {
        int[] Key = new int[64];
        String input = commomFunc.hexToBinary(key);
        // for (int i = 0; i < input.length(); ++i) {
        // Key[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        // }
        for (int index = 0; index < input.length(); index++) {
            char character = input.charAt(index); // Get the current character from the input string
            int binaryValue = Character.getNumericValue(character); // Convert the character to its numeric value
            Key[index] = binaryValue; // Store the numeric value in the Key array at the corresponding index
        }
        // pC1
        final int[] PC1 = commomFunc.PC1;
        int[] PKey = new int[56];

        // Iterate over the PC1 permutation array
        for (int i = 0; i < PC1.length; ++i) {
            // PC1 contains positions that start from 1, hence subtract 1 to align with
            // Java's 0-based indexing.
            int sourceIndex = PC1[i] - 1; // Adjust for 0-based index

            // Assign the value from the Key array using the permuted index to the PKey
            // array.
            PKey[i] = Key[sourceIndex];

            // This effectively permutes the original Key array according to the PC1 table,
            // selecting 56 bits out of the original 64 in a specific order to form PKey.
        }

        // int space = 7;

        int[] C0 = new int[28];
        int[] D0 = new int[28];

        // Copy the first 28 elements of PKey to C0
        System.arraycopy(PKey, 0, C0, 0, 28);

        // Copy the next 28 elements of PKey (starting from index 28) to D0
        System.arraycopy(PKey, 28, D0, 0, 28);

        int[][] C = new int[16][28]; // contain for C1 to C16
        int[][] D = new int[16][28]; // contain for D1 to D16

        for (int i = 0; i < 16; i++) {
            int shift = (i == 0 || i == 1 || i == 8 || i == 15) ? 1 : 2;
            C0 = commomFunc.leftRotate(C0, shift); // Apply rotation to C0
            D0 = commomFunc.leftRotate(D0, shift); // Apply rotation to D0
            C[i] = C0;
            D[i] = D0;
        }

        //////////////// PC2//////////////////////
        final int[] PC2 = commomFunc.PC2;

        int[][] K = new int[16][48];// we have 16 Key and each key is 48 bit
        // from K1 to K16
        // Assuming PC2 is split into two parts: one for indices referring to C and one
        // for D
        int[] PC2_C = Arrays.stream(PC2).filter(x -> x < 29).toArray();
        int[] PC2_D = Arrays.stream(PC2).filter(x -> x >= 29).map(x -> x - 28).toArray();

        for (int i = 0; i < 16; i++) {
            // Process parts referring to C
            for (int j = 0; j < PC2_C.length; j++) {
                K[i][j] = C[i][PC2_C[j] - 1];
            }
            // Process parts referring to D, adjust index for K accordingly
            for (int j = 0; j < PC2_D.length; j++) {
                K[i][j + PC2_C.length] = D[i][PC2_D[j] - 1];
            }
        }

        // for (int i = 0; i < 16; i++) {
        //     //System.out.print("K" + (i + 1) + ": ");
        //     for (int j = 0; j < 48; j++) {
        //         // Presumed missing line: printing K[i][j]
        //         //System.out.print(K[i][j]); // Assuming K[i][j] is to be printed

        //         // Insert a space after every 6 elements without using an additional variable
        //         if ((j + 1) % 6 == 0) {
        //             //System.out.print(" ");
        //         }
        //     }
        //     //System.out.println(); // Move to the next line after printing each row
        // }

        final int[] IP = commomFunc.IP;

        input = data; // Assuming 'data' is your hex input
        input = commomFunc.hexToBinary(input); // Convert hex to binary string

        // Assuming the binary string is always correctly 64 bits for DES
        int[] M = new int[64];
        for (int i = 0; i < input.length(); ++i) {
            M[i] = input.charAt(i) - '0'; // Directly convert char to int ('0' to 0, '1' to 1)
        }

        StringBuilder sb = new StringBuilder("M = ");
        for (int i = 0; i < M.length; i++) {
            sb.append(M[i]);
            if ((i + 1) % 4 == 0 && i < M.length - 1) { // Append a space after every 4 digits, except for the last
                                                        // group
                sb.append(' ');
            }
        }
        //System.out.println(sb.toString());

        // Apply Initial Permutation (IP) to the message
        int[] PM = new int[64];
        for (int i = 0; i < IP.length; i++) {
            PM[i] = M[IP[i] - 1]; // Use IP to permute M directly
        }

        // More efficient printing using StringBuilder for concatenation
        StringBuilder permutedOutput = new StringBuilder("IP = ");
        for (int i = 0; i < PM.length; i++) {
            permutedOutput.append(PM[i]);
            if ((i + 1) % 4 == 0 && i < PM.length - 1) { // Add space every 4 bits, not at the end
                permutedOutput.append(' ');
            }
        }
        //System.out.println(permutedOutput.toString());

        // Initialize L and R arrays for further processing
        int[][] L = new int[18][32];
        int[][] R = new int[18][32];
        for (int i = 0; i < 32; i++) {
            L[0][i] = PM[i]; // Frst half of PM to L[0]
            R[0][i] = PM[i + 32]; // Second half of PM to R[0]
        }

        // for the first R0 and L0
        // Efficiently copy the first 32 bits of PM to L[0]
        System.arraycopy(PM, 0, L[0], 0, 32);

        // Efficiently copy the next 32 bits of PM to R[0]
        System.arraycopy(PM, 32, R[0], 0, 32);

        for (int round = 0; round < 16; ++round) {
            System.arraycopy(R[round], 0, L[round + 1], 0, 32);

            // Use the E Bit-selection table directly from commomFunc
            int[] ER = new int[48];
            for (int i = 0; i < commomFunc.E.length; i++) {
                ER[i] = R[round][commomFunc.E[i] - 1];
            }

            // Combine XOR with key and E(Rn-1) in one step without intermediate printing
            // logic
            int[] KER = new int[48]; // Change to int array for direct manipulation
            for (int i = 0; i < 48; i++) {
                KER[i] = ER[i] ^ K[round][i];
            }

            // Convert KER to String for sBox method if necessary
            StringBuilder KERString = new StringBuilder();
            for (int bit : KER) {
                KERString.append(bit);
            }
            String sBoxValues = commomFunc.sBox(KERString.toString());

            // Prepare the 'f' function result directly
            int[] f = new int[32];
            for (int i = 0; i < commomFunc.P.length; i++) {
                f[i] = Character.getNumericValue(sBoxValues.charAt(commomFunc.P[i] - 1));
            }

            // Perform the Rn+1 calculation
            for (int i = 0; i < 32; i++) {
                R[round + 1][i] = L[round][i] ^ f[i];
            }
        }

        // for (int round = 0; round < 17; round++) {
        // for (int i = 0; i < 32; i++) {
        // // Assuming there's an operation to perform every 4 iterations
        // if ((i + 1) % 4 == 0) {
        // // Perform the operation here
        // }
        // }
        // }

        int[] RL = new int[64];

        // Copy the first half from R[16] and the second half from L[16]
        for (int i = 0; i < 32; i++) {
            RL[i] = R[16][i]; // Copy the first half from R[16]
            RL[i + 32] = L[16][i]; // Copy the second half from L[16]
        }

        final int[] FP = commomFunc.FP;

        StringBuilder inverseIPBuilder = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            inverseIPBuilder.append(RL[FP[i] - 1]);
        }

        String inverseIP = inverseIPBuilder.toString();

        return commomFunc.binaryToHex(inverseIP);
    }
}