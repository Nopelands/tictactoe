import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class game {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //menu
        boolean quit = false;
        boolean hard = false;
        while (!quit) {
            System.out.println("new game        -n\nquit            -q");
            boolean comando_menu_valido = false;
            while (!comando_menu_valido) {
                String comando = in.nextLine();
                switch (comando) {
                    case "quit":
                    case "q":
                    case "-q":
                        comando_menu_valido = true;
                        quit = true;
                        break;
                    case "new game":
                    case "n":
                    case "-n":
                        comando_menu_valido = true;
                        Board board = new Board();
                        System.out.println("difficulty selection:\n\neasy            -e\nhard            -h");
                        boolean dificuldade_valida = false;
                        while (!dificuldade_valida) {
                            String dificuldade = in.nextLine();
                            if (dificuldade.equals("easy") || dificuldade.equals("e") || dificuldade.equals("-e")) {
                                dificuldade_valida = true;
                            } else if (dificuldade.equals("hard") || dificuldade.equals("h") || dificuldade.equals("-h")) {
                                dificuldade_valida = true;
                                hard = true;
                            }
                        }
                        //TODO medium difficulty
                        //TODO player selection
                        if (!hard) {
                            boolean stop = false;
                            while (!stop) {
                                cpu_play_easy(board);
                                board.print_board_state();
                                if (board.win_check("X")) {
                                    stop = true;
                                    System.out.println("CPU wins");
                                } else if (board.count_empty_spots().length == 0) {
                                    System.out.println("draw");
                                    stop = true;
                                } else {
                                    boolean valid_move;
                                    String player_move;
                                    player_move = in.nextLine();
                                    valid_move = board.is_legal_move(convert_notation(player_move));
                                    if (!valid_move) {
                                        System.out.println("illegal move");
                                    }
                                    while (!valid_move) {
                                        player_move = in.nextLine();
                                        valid_move = board.is_legal_move(convert_notation(player_move));
                                        System.out.println("illegal move");
                                    }
                                    board.place_on_board("O", convert_notation(player_move));
                                    board.print_board_state();
                                    if (board.win_check("O")) {
                                        stop = true;
                                        System.out.println("Player wins");
                                    }
                                }
                            }
                        } else {
                            boolean stop = false;
                            while (!stop) {
                                cpu_play_hard(board);
                                board.print_board_state();
                                if (board.win_check("X")) {
                                    stop = true;
                                    System.out.println("CPU wins");
                                } else if (board.count_empty_spots().length == 0) {
                                    System.out.println("draw");
                                    stop = true;
                                } else {
                                    boolean valid_move;
                                    String player_move;
                                    player_move = in.nextLine();
                                    valid_move = board.is_legal_move(convert_notation(player_move));
                                    if (!valid_move) {
                                        System.out.println("illegal move");
                                    }
                                    while (!valid_move) {
                                        player_move = in.nextLine();
                                        valid_move = board.is_legal_move(convert_notation(player_move));
                                        System.out.println("illegal move");
                                    }
                                    board.place_on_board("O", convert_notation(player_move));
                                    board.print_board_state();
                                    if (board.win_check("O")) {
                                        stop = true;
                                        System.out.println("Player wins");
                                    }
                                }
                            }

                        }
                        break;
                }
            }
        }
    }

    private static void cpu_play_hard(Board board) {
        HashMap<String, Integer> answer;
        answer = minimax(board, "X");
        int move = Integer.parseInt(answer.get("index").toString());
        board.place_on_board("X", move);
    }
    private static HashMap minimax(Board board, String player) {
        String[] function_board = board.get_board_state();
        Board new_board = new Board(function_board);
        int[] empty_spaces = board.count_empty_spots();
        if (empty_spaces.length == 0) {
            HashMap<String,Integer> move = new HashMap<>();
            move.put("score", 0);
            return move;
        } else if (new_board.win_check("X")) {
            HashMap<String,Integer> move = new HashMap<>();
            move.put("score", 10);
            return move;
        } else if (new_board.win_check("O")) {
            HashMap<String,Integer> move = new HashMap<>();
            move.put("score", -10);
            return move;
        }
        ArrayList<HashMap> possibilities = new ArrayList<>();
        for (int empty_space : empty_spaces) {
            HashMap<String, Integer> move = new HashMap<>();
            move.put("index", Integer.parseInt(new_board.get_field(empty_space)));
            new_board.place_on_board(player, empty_space);
            if (player.equals("X")) {
                move.put("score", Integer.parseInt(minimax(new_board, "O").get("score").toString()));
            } else {
                move.put("score", Integer.parseInt(minimax(new_board, "X").get("score").toString()));
            }
            Integer fill = empty_space;
            new_board.place_on_board(fill.toString(), fill);
            possibilities.add(move);
        }
        int best_move = 0;
        if (player.equals("X")) {
            int best_score = Integer.MIN_VALUE;
            for (int i = 0; i < possibilities.size(); i++) {
                if (Integer.parseInt(possibilities.get(i).get("score").toString()) > best_score) {
                    best_score = Integer.parseInt(possibilities.get(i).get("score").toString());
                    best_move = i;
                }
            }
        } else {
            int best_score = Integer.MAX_VALUE;
            for (int i = 0; i < possibilities.size(); i++) {
                if (Integer.parseInt(possibilities.get(i).get("score").toString()) < best_score) {
                    best_score = Integer.parseInt(possibilities.get(i).get("score").toString());
                    best_move = i;
                }
            }
        }
        return possibilities.get(best_move);
    }
    private static void cpu_play_easy(Board board) {
        int move = ThreadLocalRandom.current().nextInt(1, 10);

        while (!board.is_legal_move(move)) {
            move = ThreadLocalRandom.current().nextInt(0, 10);
        }
        board.place_on_board("X", move);
    }
    private static int convert_notation(String position) {
        switch (position) {
            case "A1":
                return 0;
            case "A2":
                return 1;
            case "A3":
                return 2;
            case "B1":
                return 3;
            case "B2":
                return 4;
            case "B3":
                return 5;
            case "C1":
                return 6;
            case "C2":
                return 7;
            case "C3":
                return 8;
            default:
                return 999;
        }
    }
    private static boolean is_integer(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static class Board {
        private String[] board_state;

        public Board(String[] board_state) {
            this.board_state = board_state;
        }
        public Board() {
            this.board_state = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        }
        public String[] get_board_state() {
            return board_state;
        }

        public int[] count_empty_spots() {
            ArrayList<Integer> resposta = new ArrayList();
            for (String s : board_state) {
                if (is_integer(s)) {
                    resposta.add(Integer.parseInt(s));
                }
            }
            int[] result = new int[resposta.size()];
            Iterator<Integer> iterator = resposta.iterator();
            for (int i = 0; i < result.length; i++)
            {
                result[i] = iterator.next();
            }
            return result;
        }

        public boolean is_legal_move(int location) {
            switch (location) {
                case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:
                    return !board_state[location].equals("X") && !board_state[location].equals("O");
                default:
                    return false;
            }
        }

        public String get_field(int location) {
            return board_state[location];
        }

        public void place_on_board(String player_type, int location) {
            board_state[location] = player_type;
        }

        public boolean win_check(String player) {
            return (board_state[0].equals(player) && board_state[1].equals(player) && board_state[2].equals(player)) ||
                    (board_state[3].equals(player) && board_state[4].equals(player) && board_state[5].equals(player)) ||
                    (board_state[6].equals(player) && board_state[7].equals(player) && board_state[8].equals(player)) ||
                    (board_state[0].equals(player) && board_state[3].equals(player) && board_state[6].equals(player)) ||
                    (board_state[1].equals(player) && board_state[4].equals(player) && board_state[7].equals(player)) ||
                    (board_state[2].equals(player) && board_state[5].equals(player) && board_state[8].equals(player)) ||
                    (board_state[0].equals(player) && board_state[4].equals(player) && board_state[8].equals(player)) ||
                    (board_state[2].equals(player) && board_state[4].equals(player) && board_state[6].equals(player));
        }

        public void print_board_state() {
            System.out.println("\\|1|2|3|");
            System.out.println("────────");
            System.out.print("A|");
            for (int i = 0; i < 3; i++) {
                if (is_integer(board_state[i])) {
                    System.out.print(" |");
                } else {
                    System.out.print(board_state[i] + "|");
                }
            }
            System.out.print("\n");
            System.out.println("────────");
            System.out.print("B|");
            for (int i = 3; i < 6; i++) {
                if (is_integer(board_state[i])) {
                    System.out.print(" |");
                } else {
                    System.out.print(board_state[i] + "|");
                }
            }
            System.out.print("\n");
            System.out.println("────────");
            System.out.print("C|");
            for (int i = 6; i < 9; i++) {
                if (is_integer(board_state[i])) {
                    System.out.print(" |");
                } else {
                    System.out.print(board_state[i] + "|");
                }
            }
            System.out.print("\n\n");
        }
    }
}
