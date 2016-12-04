import hw.sudoku._

object Solution extends SudokuLike {
	type T = Board

	def parse(str: String): Board = {
		val allPossibleCoords = makeTuples(0, 8, 0.to(8))
		val strList = str.toList
		val blankMapping = allPossibleCoords.map(x => Map(x -> 1.to(9).toList)).flatten.toMap
		val b = new Board(blankMapping)
		placer(allPossibleCoords, strList, b)
	}

	def placer(possibles: List[(Int, Int)], vals: List[Char], currboard: Board): Board = (possibles, vals) match{
		case (Nil, Nil) => currboard
		case (h1 :: t1, h2 :: t2) => {
			if(h2 != '.'){
				placer(t1, t2, currboard.place(h1._1, h1._2, h2.asDigit))
			}
			else{
				placer(t1, t2, currboard)
			}
		}
		case _ => currboard
	}

	def associate(values: List[Char], currInd: Int): List[Map[(Int, Int), List[Char]]] = values match {
		case Nil => Nil
		case h :: t => {
			val row = currInd / 9
			val col = currInd % 9
			if(h != '.'){
				Map((row, col)->List(h)) :: associate(t, currInd + 1)
			}
			else{
				Map((row, col)->List('.')) :: associate(t, currInd + 1)
			}	
		}
	}

	def peers(row: Int, col: Int): List[(Int, Int)] = {
		val allPossibleCoords = makeTuples(0, 8, 0.to(8))
		val peerswithoutblock = allPossibleCoords.filter(x => (x._1 == row || x._2 == col) && ((x._1, x._2) != (row, col)))
	
		if(row < 3){
			if(col < 3){
				makeTuples(0, 2, 0.to(2)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
			else if(col > 5){
				makeTuples(0, 2, 6.to(8)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
			else{
				makeTuples(0, 2, 3.to(5)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
		}
		else if(row > 5){
			if(col < 3){
				makeTuples(6, 8, 0.to(2)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
			else if(col > 5){
				makeTuples(6, 8, 6.to(8)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
			else{
				makeTuples(6, 8, 3.to(5)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
		}
		else{
			if(col < 3){
				makeTuples(3, 5, 0.to(2)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
			else if(col > 5){
				makeTuples(3, 5, 6.to(8)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct
			}
			else{
				makeTuples(3, 5, 3.to(5)).union(peerswithoutblock).filter(x => (x._1, x._2) != (row, col)).distinct	
			}
		}
		
	}

	def makeTuples(curr: Int, currBound: Int, rng: Range): List[(Int,Int)] = {
		(curr to currBound).flatMap(x => (rng).map(y => (x, y))).toList
	}
}

class Board(val available: Map[(Int, Int), List[Int]]) extends BoardLike[Board] {
	import Solution._

	def availableValuesAt(row: Int, col: Int): List[Int] = {
		available.getOrElse((row, col), 1.to(0).toList)
	}

	def valueAt(row: Int, col: Int): Option[Int] = {
		val desired = available.toList.filter(x => x._1 == (row, col))(0)._2
		if(desired.size == 1){
			Some(desired(0))
		}
		else{
			None
		}
	}

	def isSolved(): Boolean = {
		val desired = available.toList.filter(x => x._2.size == 1)
		if(desired.size == 81){
			true;
		}
		else{
			false
		}
	}

	def isUnsolvable(): Boolean = {
		val desired = available.toList.filter(x => x._2.size == 0)
		if(desired.size != 0){
			true
		}
		else{
			false
		}
	}

	def place(row: Int, col: Int, value: Int): Board = {
		require(availableValuesAt(row, col).contains(value))
		val listofpeers = peers(row, col)
		val newBoard = available + ((row, col) -> List(value))
		new Board(newPlaceHelper(newBoard, listofpeers, value))
	}

	def newPlaceHelper(toberet: Map[(Int, Int), List[Int]], listofpeers: List[(Int, Int)], e: Int): Map[(Int, Int), List[Int]] = listofpeers match{
		case h :: t => {
			if(toberet(h).contains(e)){
				val newtoberet = toberet + (h -> toberet(h).filter(x => x != e))
				if(newtoberet(h).size == 1){
					val newpeers = peers(h._1, h._2)
					val newer = newPlaceHelper(newtoberet, newpeers,newtoberet(h)(0))
					newPlaceHelper(newer, t, e)
				}
				else{
					newPlaceHelper(newtoberet, t, e)
				}
			}
			else{
				newPlaceHelper(toberet, t, e)
			}
		}
		case Nil => toberet
	}  

	def nextStates(): List[Board] = {
		val firstdesired = available.toList.map{x =>
			if(x._2.size != 1){
				listPerCoordinate(x._1, x._2)
			}
			else{
				List()	
			}
		}.flatten
		val findesired = firstdesired.sortBy(x => x.available.toList.map(y => y._2).flatten.size).distinct.filter(x => x != None)
		findesired
	}

	def listPerCoordinate(coord: (Int, Int), curr: List[Int]): List[Board] = curr match{
		case Nil => Nil
		case h :: t => {
			place(coord._1, coord._2, h) :: listPerCoordinate(coord, t)
		}
	}

	def solve(): Option[Board] = {
		if(this.isUnsolvable()){
			None
		}
		else if(this.isSolved()){
			Some(this)
		}
		else{
			val nexts = this.nextStates()
			solveHelp(nexts)
		}
	}

	def solveHelp(list: List[Board]): Option[Board] = list match{
		case Nil => None
		case h :: t => {
			if(h.isSolved()){
				Some(h)
			}
			else{
				val currSolve = h.solve()
				if(currSolve != None && currSolve.get.isSolved()){
					currSolve
				}
				else{
					solveHelp(t)
				}
			}
		}
	}
}