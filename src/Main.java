import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import javax.sound.sampled.*;

public class Main {
    static int counter = 0;
    public static void main(String[] args) {
        File file = new File("resource\\rushHour.wav");
        try (AudioInputStream audio = AudioSystem.getAudioInputStream(file)){
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }catch (Exception e) {
            System.out.println("Something went wrong with audio player");
        }
        System.out.print("\033c");
        System.out.println(
        "╦ ╦┌─┐┬  ┌─┐┌─┐┌┬┐┌─┐  ┌┬┐┌─┐\n" +
        "║║║├┤ │  │  │ ││││├┤    │ │ │\n" +
        "╚╩╝└─┘┴─┘└─┘└─┘┴ ┴└─┘   ┴ └─┘"
        );
        System.out.println("888~-_                   888           888   |                               ,d88~~\\          888                             d8b     ,d  d8b     ,d ");
        System.out.println("888   \\  888  888  d88~\\ 888-~88e      888___|  e88~-_  888  888 888-~\\      8888     e88~-_  888 Y88b    /  e88~~8e  888-~\\ !Y88! ,d888 !Y88! ,d888 ");
        System.out.println("888    | 888  888 C888   888  888      888   | d888   i 888  888 888         `Y88b   d888   i 888  Y88b  /  d888  88b 888     Y8Y    888  Y8Y    888 ");
        System.out.println("888   /  888  888  Y88b  888  888      888   | 8888   | 888  888 888          `Y88b, 8888   | 888   Y88b/   8888__888 888      8     888   8     888 ");
        System.out.println("888_-~   888  888   888D 888  888      888   | Y888   ' 888  888 888            8888 Y888   ' 888    Y8/    Y888    , 888      e     888   e     888 ");
        System.out.println("888 ~-_  \"88_-888 \\_88P  888  888      888   |  \"88_-~  \"88_-888 888         \\__88P'  \"88_-~  888     Y      \"88___/  888     \"8\"    888  \"8\"    888 ");

        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nama file txt yang ada di folder test (misal: input1): ");
        String filename = input.nextLine();
        File inputFile = new File("..\\test\\" + filename + ".txt");

        while (!validateInputFile(inputFile)) {
            System.out.println("File tidak ditemukan atau format salah. Silakan masukkan nama file yang benar.");
            System.out.print("Masukkan nama file txt yang ada di folder test (misal: input1): ");
            filename = input.nextLine();
            inputFile = new File("..\\test\\" + filename + ".txt");
        }

        int A = 0, B = 0, N = 0;
        char[][] papan = null;
        int tujuanKolom = -1;
        int tujuanBaris = -1;
        Map<Character, Map<String, Integer>> huruf = new HashMap<>();

        try (Scanner fileScanner = new Scanner(inputFile)) {
            if (fileScanner.hasNextLine()) {
            String[] ab = fileScanner.nextLine().trim().split("\\s+");
            A = Integer.parseInt(ab[0]);
            B = Integer.parseInt(ab[1]);
            }
            if (fileScanner.hasNextLine()) {
            N = Integer.parseInt(fileScanner.nextLine().trim());
            }
            papan = new char[A][B];
            String baris;
            for (int i = 0; i < A; i++) {
            if (!fileScanner.hasNextLine()) break;
            baris = fileScanner.nextLine();
            int indeks = 0;
            if (baris.length() < B || (baris.charAt(0) == ' ' && baris.charAt(1) == ' ')) {
                tujuanBaris = 0;
                for (int j = 0; j < baris.length(); j++) {
                if (baris.charAt(j) == 'X') {
                    tujuanKolom = j;
                }
                }
                if (fileScanner.hasNextLine()) baris = fileScanner.nextLine();
            } else if (baris.length() > B) {
                if (baris.charAt(0) == ' ') {
                indeks = 1;
                } else {
                tujuanBaris = i;
                if (baris.charAt(0) == 'X') {
                    tujuanKolom = 0;
                    indeks = 1;
                } else {
                    tujuanKolom = B - 1;
                }
                }
            }
            for (int j = indeks; j < indeks + B; j++) {
                char x = baris.charAt(j);
                papan[i][j - indeks] = x;
                if (x != '.' && !huruf.containsKey(x)) {
                Map<String, Integer> detail = new HashMap<>();
                detail.put("baris", i);
                detail.put("kolom", j - indeks);
                detail.put("panjang", 2); //set defaultnya 2
                if (j + 1 < indeks + B && baris.charAt(j + 1) == x) {
                    detail.put("orientasi", 1); // 1 kalo horizontal, 2 kalo vertikal
                    if (j + 2 < indeks + B && baris.charAt(j + 2) == x) {
                    detail.put("panjang", 3);
                    }
                } else {
                    detail.put("orientasi", 2);
                }
                huruf.put(x, detail);
                }
            }
            }
            if (tujuanBaris == -1 && fileScanner.hasNextLine()) {
            tujuanBaris = A - 1;
            baris = fileScanner.nextLine();
            for (int j = 0; j < baris.length(); j++) {
                if (baris.charAt(j) == 'X') {
                tujuanKolom = j;
                }
            }
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan saat membaca file input.");
            input.close();
            return;
        }
        System.out.println("A: " + A + ", B: " + B + ", N: " + N);

        String pilihan = "4";
        while (!pilihan.equals("1") && !pilihan.equals("2") && !pilihan.equals("3")) {
            System.out.println("Pilih Algoritma Pencarian");
            System.out.println("1. Uniform Cost Search");
            System.out.println("2. Greedy Best First Search");
            System.out.println("3. A*");
            pilihan = input.nextLine();
        }
        String heu = "3";
        if (pilihan.equals("2") || pilihan.equals("3")) {
            while (!heu.equals("1") && !heu.equals("2")) {
            System.out.println("Pilih Heuristik");
            System.out.println("1. Jumlah mobil yang menghalangi");
            System.out.println("2. Jarak mobil ke pintu keluar");
            heu = input.nextLine();
            }
        }
        // cek panjang piece vertikal, ubah kalo ternyata panjangnya 3
        for (Map.Entry<Character, Map<String, Integer>> entry : huruf.entrySet()) {
            Map<String, Integer> detail = entry.getValue();
            Integer orientasi = detail.get("orientasi");
            if (orientasi == 2) {
                int row = detail.get("baris");
                int col = detail.get("kolom");
                if(row+2 < B && papan[row][col]==papan[row+2][col]){
                    int panjang = detail.get("panjang");
                    detail.put("panjang", panjang+1);
                }
            }
        }
        counter = 0;
        long start = System.currentTimeMillis();
        List<Langkah> langkah = new LinkedList<>();
        if(pilihan.equals("1")){
            System.out.println("Hasil pencarian UCS");
            langkah = new Main().UCS(papan, huruf, tujuanBaris, tujuanKolom, A, B);
        }else if(pilihan.equals("2")){
            System.out.println("Hasil pencarian GBFS");
            langkah = new Main().GBFS(papan, huruf, tujuanBaris, tujuanKolom, A, B, heu);
        }else if(pilihan.equals("3")){
            System.out.println("Hasil pencarian A*");
            langkah = new Main().AStar(papan, huruf, tujuanBaris, tujuanKolom, A, B, heu);
        }
        if(langkah.size() == 0){
            System.out.println("Tidak ditemukan solusi (hapus spasi di akhir tiap baris di file input)");
            input.close();
            return;
        }
        long end = System.currentTimeMillis();
        StringBuilder output = new StringBuilder()
                .append("Papan awal\n")
                .append(cetakPapan(papan, -1, -1, 0, 1));
        int gerakan = 1;
        for (Langkah l : langkah) {
            output.append(System.lineSeparator());
            new Main().ubahPapan(papan, l, huruf);
            String arah = "";
            if(huruf.get(l.piece).get("orientasi") == 1){
                if(l.jarak > 0){
                    arah = l.piece + "-kanan " + l.jarak;
                }else if(l.jarak < 0){
                    arah = l.piece + "-kiri " + Math.abs(l.jarak);
                }
            }else{
                if(l.jarak > 0){
                    arah = l.piece + "-bawah " + l.jarak;
                }else if(l.jarak < 0){
                    arah = l.piece + "-atas " + Math.abs(l.jarak);
                }
            }
            output.append("Gerakan ke-" + gerakan + ": " + arah);
            output.append(System.lineSeparator());
            output.append(cetakPapan(papan, huruf.get(l.piece).get("baris"), huruf.get(l.piece).get("kolom"), huruf.get(l.piece).get("panjang"), huruf.get(l.piece).get("orientasi")));
            gerakan++;
        }
        output.append(System.lineSeparator());
        System.out.println(output.toString());
        System.out.println("Waktu eksekusi: " + (end - start) + " ms");
        System.out.println("Jumlah state yang dicari: " + counter);
        System.out.println("Apakah ingin menyimpan hasil ke file? (y/n)");
        String save = input.nextLine();
        if (save.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file output (tanpa ekstensi): ");
            String outputFilename = input.nextLine();
            File outputFile = new File("..\\test\\" + outputFilename + ".txt");
            String cleanedOutput = output.toString().replaceAll("\u001B\\[[;\\d]*m", "");

            try (java.io.PrintWriter writer = new java.io.PrintWriter(outputFile)) {
                writer.println(cleanedOutput);
                System.out.println("Hasil telah disimpan ke " + outputFile.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan saat menyimpan file.");
            }
        }

        input.close();
    }

    public static boolean validateInputFile(File file) {
        if (!file.exists() || !file.isFile()) return false;
        try (Scanner sc = new Scanner(file)) {
            if (!sc.hasNextLine()) return false;
            String[] ab = sc.nextLine().trim().split("\\s+");
            if (ab.length != 2) return false;
            int A = Integer.parseInt(ab[0]);
            if (!sc.hasNextLine()) return false;
            int lineCount = 0;
            while (sc.hasNextLine() && lineCount < A) {
                sc.nextLine();
                lineCount++;
            }
            return lineCount == A;
        } catch (Exception e) {
            return false;
        }
    }

    public void ubahPapan(char[][] papan, Langkah l, Map<Character, Map<String, Integer>> huruf){
        int barisLama = huruf.get(l.piece).get("baris");
        int kolomLama = huruf.get(l.piece).get("kolom");
        if(huruf.get(l.piece).get("orientasi") == 1){
            for(int i = 0; i<huruf.get(l.piece).get("panjang"); i++){
                papan[barisLama][kolomLama+i] = '.';
            }
            for (int i = 0; i<huruf.get(l.piece).get("panjang"); i++) {
                papan[barisLama][kolomLama+i+l.jarak] = l.piece;
            }
            huruf.get(l.piece).put("kolom", kolomLama + l.jarak);
        }else{
            for(int i = 0; i<huruf.get(l.piece).get("panjang"); i++){
                papan[barisLama+i][kolomLama] = '.';
            }
            for (int i = 0; i < huruf.get(l.piece).get("panjang"); i++) {
                papan[barisLama+i+l.jarak][kolomLama] = l.piece;
            }
            huruf.get(l.piece).put("baris", barisLama + l.jarak);
        }
    }

    public List<Langkah> AStar(char[][] papan, Map<Character, Map<String, Integer>> huruf, int tujuanBaris, int tujuanKolom, int A, int B, String heuristik) {
        List<State> tree = new LinkedList<>();
        List<Langkah> langkah = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        State awal = new State(papan, 0, huruf, langkah, visited);
        tree.add(awal);
        while(finish(tree.get(0).pieces, tujuanBaris, tujuanKolom) == false){
            State current = tree.get(0);
            tree.remove(0);
            Map<Character, Map<String, Integer>> pieces = current.pieces;
            String visitedString = hurufToString(current.pieces);
            if(!current.visited.contains(visitedString)){
                current.visited.add(visitedString);
                List<Langkah> langkahBaru = possibleMove(current.board, current.pieces, A, B);
                for (Langkah l : langkahBaru) {
                    Map<Character, Map<String, Integer>> piecesBaru = new HashMap<>();
                    for (Map.Entry<Character, Map<String, Integer>> entry : current.pieces.entrySet()) {
                        Map<String, Integer> innerMap = new HashMap<>();
                        for (Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
                            innerMap.put(innerEntry.getKey(), innerEntry.getValue());
                        }
                        piecesBaru.put(entry.getKey(), innerMap);
                    }
                    int kolomLama = pieces.get(l.piece).get("kolom");
                    int barisLama = pieces.get(l.piece).get("baris");
                    List<Langkah> step = new LinkedList<>(current.steps);
                    step.add(l);
                    char[][] boardBaru = new char[A][B];
                    for (int i = 0; i < A; i++) {
                        System.arraycopy(current.board[i], 0, boardBaru[i], 0, B);
                    }
                    if(piecesBaru.get(l.piece).get("orientasi") == 1){
                        for(int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++){
                            boardBaru[barisLama][kolomLama+i] = '.';
                        }
                        for (int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++) {
                            boardBaru[barisLama][kolomLama+i+l.jarak] = l.piece;
                        }
                    }else{
                        for(int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++){
                            boardBaru[barisLama+i][kolomLama] = '.';
                        }
                        for (int i = 0; i < piecesBaru.get(l.piece).get("panjang"); i++) {
                            boardBaru[barisLama+i+l.jarak][kolomLama] = l.piece;
                        }
                    }
                    if(piecesBaru.get(l.piece).get("orientasi") == 1){
                        piecesBaru.get(l.piece).put("kolom", kolomLama + l.jarak);
                    }else{
                        piecesBaru.get(l.piece).put("baris", barisLama + l.jarak);
                    }
                    int heuristikValue  = current.heuristic;
                    if(heuristik.equals("1")){
                        heuristikValue += mobilPenghalang(boardBaru, piecesBaru, tujuanBaris, tujuanKolom) + 1;
                    }else if(heuristik.equals("2")){
                        heuristikValue += jarak(piecesBaru, tujuanBaris, tujuanKolom) + 1;
                    }
                    State nextState = new State(boardBaru, heuristikValue, piecesBaru, step, current.visited);
                    counter++;
                    tree.add(nextState);
                }
            }
            tree.sort((s1, s2) -> Integer.compare(s1.heuristic, s2.heuristic));
        }
        return tree.get(0).steps;
    }

    public List<Langkah> GBFS(char[][] papan, Map<Character, Map<String, Integer>> huruf, int tujuanBaris, int tujuanKolom, int A, int B, String heuristik) {
        List<State> tree = new LinkedList<>();
        List<Langkah> langkah = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        State awal = new State(papan, 0, huruf, langkah, visited);
        tree.add(awal);
        counter++;
        while(finish(tree.get(0).pieces, tujuanBaris, tujuanKolom) == false){
            State current = tree.get(0);
            tree.remove(0);
            Map<Character, Map<String, Integer>> pieces = current.pieces;
            String visitedString = hurufToString(current.pieces);
            if(!current.visited.contains(visitedString)){
                current.visited.add(visitedString);
                List<Langkah> langkahBaru = possibleMove(current.board, current.pieces, A, B);
                for (Langkah l : langkahBaru) {
                    Map<Character, Map<String, Integer>> piecesBaru = new HashMap<>();
                    for (Map.Entry<Character, Map<String, Integer>> entry : current.pieces.entrySet()) {
                        Map<String, Integer> innerMap = new HashMap<>();
                        for (Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
                            innerMap.put(innerEntry.getKey(), innerEntry.getValue());
                        }
                        piecesBaru.put(entry.getKey(), innerMap);
                    }
                    int kolomLama = pieces.get(l.piece).get("kolom");
                    int barisLama = pieces.get(l.piece).get("baris");
                    List<Langkah> step = new LinkedList<>(current.steps);
                    step.add(l);
                    char[][] boardBaru = new char[A][B];
                    for (int i = 0; i < A; i++) {
                        System.arraycopy(current.board[i], 0, boardBaru[i], 0, B);
                    }
                    if(piecesBaru.get(l.piece).get("orientasi") == 1){
                        for(int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++){
                            boardBaru[barisLama][kolomLama+i] = '.';
                        }
                        for (int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++) {
                            boardBaru[barisLama][kolomLama+i+l.jarak] = l.piece;
                        }
                    }else{
                        for(int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++){
                            boardBaru[barisLama+i][kolomLama] = '.';
                        }
                        for (int i = 0; i < piecesBaru.get(l.piece).get("panjang"); i++) {
                            boardBaru[barisLama+i+l.jarak][kolomLama] = l.piece;
                        }
                    }
                    if(piecesBaru.get(l.piece).get("orientasi") == 1){
                        piecesBaru.get(l.piece).put("kolom", kolomLama + l.jarak);
                    }else{
                        piecesBaru.get(l.piece).put("baris", barisLama + l.jarak);
                    }
                    int heuristikValue  = current.heuristic;
                    if(heuristik.equals("1")){
                        heuristikValue += mobilPenghalang(boardBaru, piecesBaru, tujuanBaris, tujuanKolom);
                    }else if(heuristik.equals("2")){
                        heuristikValue += jarak(piecesBaru, tujuanBaris, tujuanKolom);
                    }
                    State nextState = new State(boardBaru, heuristikValue, piecesBaru, step, current.visited);
                    tree.add(nextState);
                    counter++;
                }
            }
            tree.sort((s1, s2) -> Integer.compare(s1.heuristic, s2.heuristic));
            if(tree.size()==0){
                return new LinkedList<>();
            }
        }
        return tree.get(0).steps;
    }

    public List<Langkah> UCS(char[][] papan, Map<Character, Map<String, Integer>> huruf, int tujuanBaris, int tujuanKolom, int A, int B) {
        List<State> tree = new LinkedList<>();
        List<Langkah> langkah = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        State awal = new State(papan, 0, huruf, langkah, visited);
        tree.add(awal);
        counter++;
        while(finish(tree.get(0).pieces, tujuanBaris, tujuanKolom) == false){
            State current = tree.get(0);
            tree.remove(0);
            Map<Character, Map<String, Integer>> pieces = current.pieces;
            String visitedString = hurufToString(current.pieces);
            if(!current.visited.contains(visitedString)){
                current.visited.add(visitedString);
                List<Langkah> langkahBaru = possibleMove(current.board, current.pieces, A, B);
                for (Langkah l : langkahBaru) {
                    Map<Character, Map<String, Integer>> piecesBaru = new HashMap<>();
                    for (Map.Entry<Character, Map<String, Integer>> entry : current.pieces.entrySet()) {
                        Map<String, Integer> innerMap = new HashMap<>();
                        for (Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
                            innerMap.put(innerEntry.getKey(), innerEntry.getValue());
                        }
                        piecesBaru.put(entry.getKey(), innerMap);
                    }
                    int kolomLama = pieces.get(l.piece).get("kolom");
                    int barisLama = pieces.get(l.piece).get("baris");
                    List<Langkah> step = new LinkedList<>(current.steps);
                    step.add(l);
                    char[][] boardBaru = new char[A][B];
                    for (int i = 0; i < A; i++) {
                        System.arraycopy(current.board[i], 0, boardBaru[i], 0, B);
                    }
                    if(piecesBaru.get(l.piece).get("orientasi") == 1){
                        for(int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++){
                            boardBaru[barisLama][kolomLama+i] = '.';
                        }
                        for (int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++) {
                            boardBaru[barisLama][kolomLama+i+l.jarak] = l.piece;
                        }
                    }else{
                        for(int i = 0; i<current.pieces.get(l.piece).get("panjang"); i++){
                            boardBaru[barisLama+i][kolomLama] = '.';
                        }
                        for (int i = 0; i < piecesBaru.get(l.piece).get("panjang"); i++) {
                            boardBaru[barisLama+i+l.jarak][kolomLama] = l.piece;
                        }
                    }
                    if(piecesBaru.get(l.piece).get("orientasi") == 1){
                        piecesBaru.get(l.piece).put("kolom", kolomLama + l.jarak);
                    }else{
                        piecesBaru.get(l.piece).put("baris", barisLama + l.jarak);
                    }
                    int heuristikValue  = current.heuristic+1;
                    State nextState = new State(boardBaru, heuristikValue, piecesBaru, step, current.visited);
                    tree.add(nextState);
                    counter++;
                }
            }
            tree.sort((s1, s2) -> Integer.compare(s1.heuristic, s2.heuristic));
            if(tree.size()==0){
                return new LinkedList<>();
            }
        }
        return tree.get(0).steps;
    }

    // Convert map huruf ke string biar bisa dicek udah dikunjungi atau belum
    public String hurufToString(Map<Character, Map<String, Integer>> huruf){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Map<String, Integer>> entry : huruf.entrySet()) {
            Character hurufKey = entry.getKey();
            Map<String, Integer> detail = entry.getValue();
            sb.append(hurufKey);
            sb.append(detail.get("baris"));
            sb.append(detail.get("kolom"));
        }
        return sb.toString();
    }

    // Return langkah yang mungkin dalam bentuk List (huruf, jarak)
    public List<Langkah> possibleMove(char[][] papan, Map<Character, Map<String, Integer>> huruf, int A, int B){
        List<Langkah> langkah = new LinkedList<>();
        for (Map.Entry<Character, Map<String, Integer>> entry : huruf.entrySet()) {
            Character hurufKey = entry.getKey();
            Map<String, Integer> detail = entry.getValue();
            int baris = detail.get("baris");
            int kolom = detail.get("kolom");
            int panjang = detail.get("panjang");
            int orientasi = detail.get("orientasi");
            int i =  1;
            if(orientasi == 1){
                while(kolom+panjang+i-1 < B && papan[baris][kolom+panjang+i-1] == '.'){
                    Langkah l = new Langkah(hurufKey, i);
                    langkah.add(l);
                    i++;
                }
                i = -1;
                while(kolom+i >= 0 && papan[baris][kolom+i] == '.'){
                    Langkah l = new Langkah(hurufKey, i);
                    langkah.add(l);
                    i--;
                }
            }else{
                while(baris+panjang+i-1 < A && papan[baris+panjang+i-1][kolom] == '.'){
                    Langkah l = new Langkah(hurufKey, i);
                    langkah.add(l);
                    i++;
                }
                i = -1;
                while(baris+i >= 0 && papan[baris+i][kolom] == '.'){
                    Langkah l = new Langkah(hurufKey, i);
                    langkah.add(l);
                    i--;
                }
            }
        }
        return langkah;
    }

    // Cek berapa mobil yang ada di antara P sampe gerbang
    public int mobilPenghalang(char[][] papan, Map<Character, Map<String, Integer>> huruf, int tujuanBaris, int tujuanKolom) {
        int penghalang = 0;
        Map<String, Integer> detail = huruf.get('P');
        int barisP = detail.get("baris");
        int kolomP = detail.get("kolom");
        if (huruf.get('P').get("orientasi") == 1) {
            int start = kolomP + detail.get("panjang");
            if (start < tujuanKolom){
                for (int kol = start; kol <= tujuanKolom; kol++) {
                    char blok = papan[barisP][kol];
                    if (blok != '.' && blok != 'P') {
                        penghalang++;
                    }
                }
            }else{
                for (int kol = kolomP - 1; kol >= tujuanKolom; kol--) {
                    char blok = papan[barisP][kol];
                    if (blok != '.' && blok != 'P') {
                        penghalang++;
                    }
                }
            }
        } else {
            int start = barisP + detail.get("panjang");
            if(start < tujuanBaris){
                for (int bar = start; bar <= tujuanBaris; bar++){
                    char blok = papan[bar][kolomP];
                    if (blok != '.' && blok != 'P'){
                        penghalang++;
                    }
                }
            } else if(start > tujuanBaris){
                for (int bar = start - 1; bar >= tujuanBaris; bar--){
                    char blok = papan[bar][kolomP];
                    if (blok != '.' && blok != 'P'){
                        penghalang++;
                    }
                }
            }
        }
        return penghalang;
    }

    // Cek jarak dari ujung P ke gerbang
    public int jarak(Map<Character, Map<String, Integer>> huruf, int tujuanBaris, int tujuanKolom){
        if(huruf.get('P').get("orientasi")==1){
            return Math.abs(huruf.get('P').get("kolom") - tujuanKolom + 1);
        }else{
            return Math.abs(huruf.get('P').get("baris") - tujuanBaris + 1);
        }
    }

    // Cek apakah sudah sampai di finish
    public static boolean finish(Map<Character, Map<String, Integer>> huruf, int tujuanBaris, int tujuanKolom){
        if(huruf.get('P').get("orientasi")==1){
            if(huruf.get('P').get("kolom")< tujuanKolom){
                return huruf.get('P').get("kolom")+huruf.get('P').get("panjang")-1 == tujuanKolom;
            }else{
                return huruf.get('P').get("kolom") == tujuanKolom;
            }
        }else{
            if(huruf.get('P').get("baris")< tujuanBaris){
                return huruf.get('P').get("baris")+huruf.get('P').get("panjang")-1 == tujuanBaris;
            }else{
                return huruf.get('P').get("baris") == tujuanBaris;
            }
        }
    }
    private static String cetakPapan(char[][] papan, int barisPindah, int kolomPindah, int panjang, int orientasi) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < papan.length; i++) {
            for (int j = 0; j < papan[i].length; j++){
                char karakter = papan[i][j];
                String teks = String.valueOf(karakter);
                if(orientasi == 1){
                    if ((barisPindah == i && j >= kolomPindah && j < kolomPindah + panjang)) {
                        output.append("\u001B[43m" + getColoredText(teks) + "\u001B[0m");
                    } else {
                        output.append(getColoredText(teks));
                    }
                }else{
                    if ((kolomPindah == j && i >= barisPindah && i < barisPindah + panjang)) {
                        output.append("\u001B[43m" + getColoredText(teks) + "\u001B[0m");
                    } else {
                        output.append(getColoredText(teks));
                    }
                }
            }
            output.append(System.lineSeparator());
        }
        return output.toString();
    }
    public static String getColoredText(String text) {
        StringBuilder coloredText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if(c == '.'){
                coloredText.append(c);
                continue;
            }
            int index = Character.toUpperCase(c) - 'A';
            coloredText.append(COLORS[index]).append(c).append(RESET);
        }
        return coloredText.toString();
    }
    public static final String RESET = "\u001B[0m";
    public static final String[] COLORS = {
        "\u001B[31m", // A - Merah
        "\u001B[34m", // B - Biru
        "\u001B[32m", // C - Hijau
        "\u001B[33m", // D - Kuning
        "\u001B[35m", // E - Ungu
        "\u001B[36m", // F - Cyan
        "\u001B[91m", // G - Merah terang
        "\u001B[94m", // H - Biru terang
        "\u001B[92m", // I - Hijau terang
        "\u001B[93m", // J - Kuning terang
        "\u001B[95m", // K - Ungu terang
        "\u001B[96m", // L - Cyan terang
        "\u001B[37m", // M - Putih
        "\u001B[90m", // N - Abu-abu
        "\u001B[97m", // O - Putih terang
        "\u001B[30m", // P - Hitam (jika latar belakang terang)
        "\u001B[41m", // Q - Background merah
        "\u001B[42m", // R - Background hijau
        "\u001B[43m", // S - Background kuning
        "\u001B[44m", // T - Background biru
        "\u001B[45m", // U - Background ungu
        "\u001B[46m", // V - Background cyan
        "\u001B[100m", // W - Background abu-abu gelap
        "\u001B[101m", // X - Background merah terang
        "\u001B[102m", // Y - Background hijau terang
        "\u001B[103m"  // Z - Background kuning terang
    };
}