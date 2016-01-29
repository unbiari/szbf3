package com.kidmobile.csr.global;

public class Constant {
	public static enum GameUserStatus {
		STATUS_USER_NONE, // just created

		STATUS_TOURNAMENT_JOINED, // blind started
		// STATUS_ROOM_JOINED,
		
		STATUS_INSPECTING,
	}

	public static enum TournamentStatus {
		STATUS_TOURNAMENT_NONE, // just created
		STATUS_TOURNAMENT_INITIALIZED, // just first player entered
		
		STATUS_TOURNAMENT_FULLED,
	}
	
	public static enum RoomStatus {
		STATUS_ROOM_NONE,
		STATUS_ROOM_INITIALIZED, // just master entered
		
		STATUS_BOARD_REPLACING, // battle started
		STATUS_BOARD_ONE_REPLACED,
		STATUS_ROOM_GAME_ING,
		STATUS_ROOM_GAME_ENDED, // before exit game
	}

	public static class MalType {
		public static final byte MAL_NONE = 0;
		public static final byte MAL_KING = 1;
		public static final byte MAL_SPY = 2;
		public static final byte MAL_ASS = 3;
		public static final byte MAL_ENG = 4;
		public static final byte MAL_5 = 5;
		public static final byte MAL_6 = 6;
		public static final byte MAL_7 = 7;
		public static final byte MAL_8 = 8;
		public static final byte MAL_MINE = 9;
	}
	
	public static int MAL_MAX = 9;
	public static int PAN_SIZE = 24;
	public static int PAN_INDEX_ARR[] = {1, 5, 10, 16, 21};
	public static int MASTER_INDEX_START = PAN_INDEX_ARR[3]; // 16;
	public static int SLAVE_INDEX_START = MAL_MAX;
	
	public static byte[][] Attack_win = { { MalType.MAL_KING }, // MAL_KING
		{ MalType.MAL_KING, MalType.MAL_SPY }, // MAL_SPY
		{ MalType.MAL_KING, MalType.MAL_8, MalType.MAL_ASS, MalType.MAL_SPY }, // MAL_ASS
		{ MalType.MAL_KING, MalType.MAL_ENG, MalType.MAL_ASS, MalType.MAL_SPY, MalType.MAL_MINE }, // MAL_ENG
		{ MalType.MAL_KING, MalType.MAL_5, MalType.MAL_ENG, MalType.MAL_ASS, MalType.MAL_SPY }, // MAL_5
		{ MalType.MAL_KING, MalType.MAL_6, MalType.MAL_5, MalType.MAL_ENG, MalType.MAL_ASS, MalType.MAL_SPY }, // MAL_6
		{ MalType.MAL_KING, MalType.MAL_7, MalType.MAL_6, MalType.MAL_5, MalType.MAL_ENG, MalType.MAL_ASS, MalType.MAL_SPY }, // MAL_7
		{ MalType.MAL_KING, MalType.MAL_8, MalType.MAL_7, MalType.MAL_6, MalType.MAL_5, MalType.MAL_ENG, MalType.MAL_SPY }, // MAL_8
		{ }, // MAL_MINE
	};

	public static int TURN_COUNT_START = 100;
	public static int INDEX_ADD = 10;
	
	public static int MAX_TURN_COUNT = 100;

	public static int MAX_BIG_DATA = 30; // 아래 정의된 갯수 보다 커야됨
	public static class BigData {
		public static final byte ATTACK_COUNT = 0;
		public static final byte ATTACK_WIN_COUNT = 1;
		public static final byte DEFENCE_COUNT = 2;
		public static final byte DEFENCE_WIN_COUNT = 3;
		public static final byte FIRST_ATTACK_FLAG = 4;
		public static final byte TOTAL_TURN_COUNT = 5;
		public static final byte TURN_OVER_COUNT = 6;
		public static final byte BACK_MOVE_COUNT = 7;
		public static final byte SPY_FIRST_LINE_FLAG = 8;
		public static final byte MINE_FIRST_LINE_FLAG = 9;
		public static final byte GF_FIRST_LINE_FLAG = 10;
		public static final byte FIRST_LINE_FIRE_VALUE = 11;
		public static final byte KING_LEFT_FLAG = 12;
		public static final byte FIRST_ACTION_MAL = 13;
		public static final byte HOUSE_KEEPER_WIN_FLAG = 14;
		public static final byte KID_WIN_FLAG = 15;
		public static final byte SAME_LEVEL_WIN_COUNT = 16;
		public static final byte LEFT_FIRE_VALUE = 17;
		public static final byte RIGHT_FIRE_VALUE = 18;
		public static final byte MINE_WIN_COUNT = 19;
		public static final byte GRAND_FATHER_WIN_COUNT = 20;
		public static final byte GRAND_MOTHER_WIN_COUNT = 21;
		public static final byte FATHER_WIN_COUNT = 22;
		public static final byte MOTHER_WIN_COUNT = 23;
		public static final byte TOTAL_GAME_TIME = 24;
		public static final byte LIVE_MAL_COUNT = 25;
		public static final byte SPY_KNOWM_MAL = 26; // 새로운 것 추가시 아랫 부분 부터 할 것
	}
}
