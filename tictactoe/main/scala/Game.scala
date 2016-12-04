import hw.tictactoe._

class Game(turn: Player, dim: Int, board: Map[(Int, Int), Player]) extends GameLike[Game]{

	val boardListOptioned = board.groupBy(x => x._1).toList.map(x => Some(x._2)) //Not very used
	val boardList = board.groupBy(x => x._1).toList.map(x => x._2)	
	def isFinished(): Boolean = {
		if(this.getWinner() != None){
			true
		}
		dim * dim == boardList.size
	}

	def getWinner(): Option[Player] = {
		val winningSetHor = this.getHorizontal(0, boardList)
		if(winningSetHor == Nil){
			val winningSetVer = this.getVertical(0, boardList)
			if(winningSetVer == Nil){
				val winningSetDiag = this.getDiagonal(boardList)
				if(winningSetDiag == Nil){
					None
				}
				else{
					Some(winningSetDiag(0)._2)
				} 
			}
			else{	
				Some(winningSetVer(0)._2)
			}
		}
		else{
			Some(winningSetHor(0)._2)
		}
	}

	def getDiagonal(currboard: List[Map[(Int, Int), Player]]): List[((Int, Int), Player)] = {
		val currDiag = currboard.flatten.filter(x => x._1._1 == x._1._2)
		if(currDiag.filter(x => x._2 == X).size == dim || currDiag.filter(x => x._2 == O).size == dim){
			currDiag
		}
		else{
			val currOtherDiag = currboard.flatten.filter(x => x._1._1 == dim - 1 - x._1._2)
			if(currOtherDiag.filter(x => x._2 == X).size == dim || currOtherDiag.filter(x => x._2 == O).size == dim){
				currOtherDiag
			}
			else{
				Nil
			}
		}
	}

	def getVertical(currColNum: Int, currboard: List[Map[(Int,Int), Player]]): List[((Int, Int), Player)] = {
		val currCol = currboard.flatten.filter(x => x._1._2 == currColNum)
		if(currCol.filter(x => x._2 == X).size == dim || currCol.filter(x => x._2 == O).size == dim){
			currCol
		}
		else{
			if(currColNum != dim){
				getVertical(currColNum + 1, currboard)			
			}
			else{
				Nil //REVISE THIS LATER
			}
		}
	}

	def getHorizontal(currRowNum: Int, currboard: List[Map[(Int,Int), Player]]): List[((Int, Int), Player)] = {
		val currRow = currboard.flatten.filter(x => x._1._1 == currRowNum)
		if(currRow.filter(x => x._2 == X).size == dim || currRow.filter(x => x._2 == O).size == dim){
			currRow
		}
		else{
			if(currRowNum != dim){
				getHorizontal(currRowNum + 1, currboard)			
			}
			else{
				Nil //REVISE THIS LATER
			}
		}
	}
	def nextBoards(): List[Game] = {
		val coords = makeTuples(0,0 to dim - 1)
		val boardcoords = boardList.flatten.map(x => x._1)
		nextBoardsRecur(coords, boardcoords)

	}
	def nextBoardsRecur(values: List[(Int, Int)], boardcoords: List[(Int, Int)]): List[Game] = values match {
		case Nil => Nil
		case h :: t => {
			if(!boardcoords.contains(h)){
				if(turn == X){
					new Game(O, dim, board ++ Map(h -> turn)) :: nextBoardsRecur(t, boardcoords)
				}
				else{
					new Game(X, dim, board ++ Map(h -> turn)) :: nextBoardsRecur(t, boardcoords)
				}
			}
			else{
				nextBoardsRecur(t, boardcoords)
			}
		}
	}	

	def makeTuples(curr: Int, rng: Range): List[(Int,Int)] = {
		if(curr < dim){
			(curr, (rng).toList.map(x => (curr, x)))._2 ++ makeTuples(curr + 1, rng)
		}
		else{
			Nil
		}
	}

	def getTurn(): Player = {
		turn
	}
}

object Solution extends MinimaxLike{
	type T = Game

	def createGame(turn: Player, dim: Int, board: Map[(Int, Int), Player]): Game = {
		new Game(turn, dim, board)
	}
	def minimax(board: Game): Option[Player] = {
		if(board.getTurn() == X){
			if(board.getWinner() == Some(X)){
				Some(X)
			}
			else if(board.getWinner() == Some(O)){
				Some(O)
			}
			else if(board.isFinished() && board.getWinner() == None){
				None
			}
			else{
				val values = board.nextBoards().map(x => minimax(x))
				if(values.contains(Some(X)) == true){
					Some(X)
				}
				else if(values.contains(None) == true){
					None
				}
				else{
					Some(O)
				}
			}
		}
		else {
			if(board.getWinner() == Some(O)){
				Some(O)
			}
			else if(board.getWinner() == Some(X)){
				Some(X)
			}
			else if(board.isFinished() && board.getWinner() == None){
				None
			}
			else{
				val values = board.nextBoards().map(x => minimax(x))
				if(values.contains(Some(O)) == true){
					Some(O)
				}
				else if(values.contains(None) == true){
					None
				}
				else{
					Some(X)
				}
			}
		}
	}
}
//TEST MINIMAX ON EMPTY 3x3 SHOULD RET NONE
