package Model.AI;

import Model.Position;
import Model.ReversiBoard;

public class AI_Sample extends AI_Base
{
	/**
	 * @param board リバーシ盤
	 * @return 置く場所を返します。
	 * */
	@Override
	public Position getNextPosition(ReversiBoard board)
	{
		return null;
	}

	//・使えるメソッド
	//Stone getTurn(void): AIの手番がStone型(Enum)で返ってきます。

	//Stone ReversiBoard.getTurn(void): boardの現在の手番がStone型で返ってきます。

	//ReversiBoard ReversiBoard.clone(boid): ReversiBoardをcloneします。
	//place系の命令を使うには必須です。

	//int[] ReversiBoard.placeAtPastAsReverse(Position): Positionにboardの現在の手番のStoneを置きます。
	//[0]: ひっくり返した石の数
	//[1]: ひっくり返した場合の開放度
	//[2]: 次の手番でひっくり返せる場所の数

	//double ReversiBoard.getStoneRate(Stone)
	//Stoneで渡した石に対する相手の比率(Stone/Enemy)が返ってきます。高いほどStone側が多いです。
}
