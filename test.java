
public class test {

    public static void main(String[] args) {
        des_e des  = new des_e();
        String cipherText = des.encrypt("fff","C000CCA");
        System.out.println(cipherText);
        des_d des_de = new des_d();
        String answer = des_de.decrypt("fff",cipherText);
        System.out.println(answer);
    }
}
