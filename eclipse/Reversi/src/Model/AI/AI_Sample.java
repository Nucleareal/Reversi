package Model.AI;

import Model.Position;
import Model.ReversiBoard;

public class AI_Sample extends AI_Base
{
	/**
	 * 注意: AI_Baseから継承されたboolean _isStopThinkingがtrueの場合は
	 * 処理を中断し、できるだけ早く結果を返す必要があります。
	 * @param board リバーシ盤
	 * @return 置く場所を返します。
	 * */
	@Override
	public Position getNextPosition(ReversiBoard board)
	{
		Position pos = placeRandomly(board, getTurn());

		return pos;
	}

	//・使えるメソッド
	//Position placeRandomly(ReversiBoard, Stone)
	//置ける適当な場所が返ってきます。

	//Stone getTurn(void): AIの手番がStone型(Enum)で返ってきます。

	//Stone ReversiBoard.getTurn(void): boardの現在の手番がStone型で返ってきます。

	//List<Position> ReversiBoard.getAllPlaceablePoints()
	//現在の盤面の、現在の手番の置ける全ての場所がListで返ってきます。

	//ReversiBoard ReversiBoard.clone(boid): ReversiBoardをcloneします。
	//place系の命令を使うには必須です。

	//int[] ReversiBoard.placeAtPastAsReverse(Position): Positionにboardの現在の手番のStoneを置きます。
	//[0]: ひっくり返した石の数
	//[1]: ひっくり返した場合の開放度
	//[2]: 次の手番でひっくり返せる場所の数

	//double ReversiBoard.getStoneRate(Stone)
	//Stoneで渡した石に対する相手の比率(Stone/Enemy)が返ってきます。高いほどStone側が多いです。
}
