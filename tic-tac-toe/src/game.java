import java.util.Scanner;

public class game {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //menu
        boolean quit = false;
        boolean hard = false;
        Board board = new Board();
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
                        int jogadas = 0;
                        //TODO game here
                        break;
                    case "step up":
                        comando_menu_valido = true;
                        System.out.print("New Game wa Now Loading");
                        for (int i = 0; i < 3; i++) {
                            sleep(3000);
                            System.out.print(".");
                        }
                        sleep(1000);
                        System.out.println("Now Loading!");
                        sleep(5000);
                        System.out.println("Now Complete!");
                        break;
                    default:
                        System.out.println("unknown command");
                        break;
                }
            }
        }
    }
    private static void cpu_play_easy(String[][] board) {
        int move = (int) (Math.random() * 9) + 1;
        while (!is_legal_move(move, board)) {
            move = (int) (Math.random() * 9) + 1;
        }
        place_on_board("X", move, board);
    }

    private static void print_board_state(String[][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i < 3) {
                    if (j < 3) {
                        System.out.print(board[i][j] + "|");
                    } else {
                        System.out.print(board[i][j] + "|" + "\n");
                        System.out.println("────────");
                    }
                } else {
                    if (j < 3) {
                        System.out.print(board[i][j] + "|");
                    } else {
                        System.out.print(board[i][j] + "|" + "\n\n");
                    }
                }
            }
        }
    }
    private static int convert_notation(String position) {
        switch (position) {
            case "A1":
                return 1;
            case "A2":
                return 2;
            case "A3":
                return 3;
            case "B1":
                return 4;
            case "B2":
                return 5;
            case "B3":
                return 6;
            case "C1":
                return 7;
            case "C2":
                return 8;
            case "C3":
                return 9;
            default:
                return 0;
        }
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static class Board {
        private String[] board_state = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        public String[] get_board_state() {
            return board_state;
        }

        public boolean is_legal_move(int location) {
            switch (location) {
                case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9:
                    if (!board_state[location].equals("X") && !board_state[location].equals("O")) {
                        return true;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }
        }
        public String get_field(int location) {
            return board_state[location];
        }
        public void place_on_board(String player_type, int location, String[] board) {
            board[location] = player_type;
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
    }
}
