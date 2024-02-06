import java.util.Arrays;

public class des_e {

    public String encrypt(String key, String data) {
        int[] Key = new int[64];
        String input = commomFunc.hexToBinary(key);
     
        for (int index = 0; index < input.length(); index++) {
            char character = input.charAt(index); 
            int binaryValue = Character.getNumericValue(character); 
            Key[index] = binaryValue; 
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

          
        }


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
        int[][] K = new int[16][48];     
        int[] PC2_C = Arrays.stream(PC2).filter(x -> x < 29).toArray();
        int[] PC2_D = Arrays.stream(PC2).filter(x -> x >= 29).map(x -> x - 28).toArray();
        for (int i = 0; i < 16; i++) {
            // Process parts referring to C
            for (int j = 0; j < PC2_C.length; j++) {
                K[i][j] = C[i][PC2_C[j] - 1];
            }
         
            for (int j = 0; j < PC2_D.length; j++) {
                K[i][j + PC2_C.length] = D[i][PC2_D[j] - 1];
            }
        }

        final int[] IP = commomFunc.IP;

        input = data; 
        input = commomFunc.hexToBinary(input); 

 
        int[] M = new int[64];
        for (int i = 0; i < input.length(); ++i) {
            M[i] = input.charAt(i) - '0'; 
        }
        StringBuilder sb = new StringBuilder("M = ");
        for (int i = 0; i < M.length; i++) {
            sb.append(M[i]);
            if ((i + 1) % 4 == 0 && i < M.length - 1) {                                                        
                sb.append(' ');
            }
        }
     

        // Apply Initial Permutation (IP) to the message
        int[] PM = new int[64];
        for (int i = 0; i < IP.length; i++) {
            PM[i] = M[IP[i] - 1]; 
        }

       
        StringBuilder permutedOutput = new StringBuilder("IP = ");
        for (int i = 0; i < PM.length; i++) {
            permutedOutput.append(PM[i]);
            if ((i + 1) % 4 == 0 && i < PM.length - 1) { 
                permutedOutput.append(' ');
            }
        }
     

        // Initialize L and R arrays for further processing
        int[][] L = new int[18][32];
        int[][] R = new int[18][32];
        for (int i = 0; i < 32; i++) {
            L[0][i] = PM[i]; // Frst half 
            R[0][i] = PM[i + 32]; // Second half 
        }

        // for the first R0 and L0     
        System.arraycopy(PM, 0, L[0], 0, 32);        
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
