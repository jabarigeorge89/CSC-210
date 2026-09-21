import java.util.ArrayList;

public class DNA {

    public static ArrayList<String> dnaToCodons(String dna) {

        ArrayList<String> codons = new ArrayList<String>();

        for (int i = 0; i < dna.length(); i = i + 3) {
            String codon = dna.substring(i, i + 3);
            codons.add(codon);
        }

        return codons;
    }

    public static String codonToAminoAcid(String codon) {

        if (codon.equals("TTT") || codon.equals("TTC"))
            return "F";

        if (codon.equals("TTA") || codon.equals("TTG") ||
            codon.equals("CTT") || codon.equals("CTC") ||
            codon.equals("CTA") || codon.equals("CTG"))
            return "L";

        if (codon.equals("ATT") || codon.equals("ATC") ||
            codon.equals("ATA"))
            return "I";

        if (codon.equals("ATG"))
            return "M";

        if (codon.equals("GTT") || codon.equals("GTC") ||
            codon.equals("GTA") || codon.equals("GTG"))
            return "V";

        if (codon.equals("TCT") || codon.equals("TCC") ||
            codon.equals("TCA") || codon.equals("TCG") ||
            codon.equals("AGT") || codon.equals("AGC"))
            return "S";

        if (codon.equals("CCT") || codon.equals("CCC") ||
            codon.equals("CCA") || codon.equals("CCG"))
            return "P";

        if (codon.equals("ACT") || codon.equals("ACC") ||
            codon.equals("ACA") || codon.equals("ACG"))
            return "T";

        if (codon.equals("GCT") || codon.equals("GCC") ||
            codon.equals("GCA") || codon.equals("GCG"))
            return "A";

        if (codon.equals("TAT") || codon.equals("TAC"))
            return "Y";

        if (codon.equals("TAA") || codon.equals("TAG") ||
            codon.equals("TGA"))
            return "Stop";

        if (codon.equals("CAT") || codon.equals("CAC"))
            return "H";

        if (codon.equals("CAA") || codon.equals("CAG"))
            return "Q";

        if (codon.equals("AAT") || codon.equals("AAC"))
            return "N";

        if (codon.equals("AAA") || codon.equals("AAG"))
            return "K";

        if (codon.equals("GAT") || codon.equals("GAC"))
            return "D";

        if (codon.equals("GAA") || codon.equals("GAG"))
            return "E";

        if (codon.equals("TGT") || codon.equals("TGC"))
            return "C";

        if (codon.equals("TGG"))
            return "W";

        if (codon.equals("CGT") || codon.equals("CGC") ||
            codon.equals("CGA") || codon.equals("CGG") ||
            codon.equals("AGA") || codon.equals("AGG"))
            return "R";

        if (codon.equals("GGT") || codon.equals("GGC") ||
            codon.equals("GGA") || codon.equals("GGG"))
            return "G";

        return "?";
    }

    public static ArrayList<String> dnaToAminoAcids(String dna) {

        ArrayList<String> codons = dnaToCodons(dna);
        ArrayList<String> aminoAcids = new ArrayList<String>();

        for (int i = 0; i < codons.size(); i++) {
            String aminoAcid = codonToAminoAcid(codons.get(i));
            aminoAcids.add(aminoAcid);
        }

        return aminoAcids;
    }

    public static boolean isMatch(ArrayList<String> aminoSeq1,
                                  ArrayList<String> aminoSeq2) {

        return aminoSeq1.equals(aminoSeq2);
    }

    public static void main(String[] args) {

        String DNA1 = "CTGATATTGTATCCGGCCGAT";
        String DNA2 = "CTAGCCGGTGGTTATTAATAGTAAACTATTCCA";
        String DNA3 = "TTAATCCTCTACCCCGCAGAC";

        ArrayList<String> amino1 = dnaToAminoAcids(DNA1);
        ArrayList<String> amino2 = dnaToAminoAcids(DNA2);
        ArrayList<String> amino3 = dnaToAminoAcids(DNA3);

        System.out.println("DNA1: " + amino1);
        System.out.println("DNA2: " + amino2);
        System.out.println("DNA3: " + amino3);

        System.out.println("DNA1 and DNA2 match: " + isMatch(amino1, amino2));
        System.out.println("DNA1 and DNA3 match: " + isMatch(amino1, amino3));
        System.out.println("DNA2 and DNA3 match: " + isMatch(amino2, amino3));
    }
}