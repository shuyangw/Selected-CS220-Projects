import Solution._
import hw.tictactoe._

class Test extends org.scalatest.FunSuite {
	val x: Player = X
	val o: Player = O

	// test("vertiTest"){
	// 	val testGame = createGame(x, 3, Map((0,0) -> x, (0,1) -> o, (0,2) -> o,
	// 										(1,0) -> o, (1,1) -> x, (1,2) -> x,
	// 										(2,0) -> o,	(2,1) -> x, (2,2) -> x))
	// 	println(testGame.getWinner())
	// }
	test("horizTest"){
		val testGame = createGame(x, 3,	Map(			(0,1) -> x,	(0,2) -> o, 
											(1,0) -> o,	(1,1) -> o, (1,2) -> x,
											(2,0) -> x, (2,1) -> x, (2,2) -> x))
		println(testGame.nextBoards())
	}
	// test("DiagTest1"){
	// 	val testGame = createGame(x, 3, Map((0,0) -> x,	(0,1) -> o, (0,2) -> x,
	// 										(1,0) -> o, (1,1) -> x, (1,2) -> o,
	// 													(2,1) -> x, (2,2) -> x))
	// 	testGame.isFinished()
	// }
	// test("DiagTestRighttoLeft"){
	// 	val testGame = createGame(x, 3, Map((0,0) -> x,	(0,1) -> o, (0,2) -> x,
	// 										(1,0) -> o, (1,1) -> x, (1,2) -> o,
	// 										(2,0) -> x,	(2,1) -> x, (2,2) -> o))
	// 	testGame.isFinished()
	// }
	// test("DiagTestDim = 4"){
	// 	val testGame = createGame(x, 4, Map((0,0) -> x,	(0,1) -> o, (0,2) -> x,	(0,3) -> o,
	// 										(1,0) -> o, (1,1) -> x, (1,2) -> o,	(1,3) -> x,
	// 										(2,0) -> x,	(2,1) -> o, (2,2) -> o,	(2,3) -> x,
	// 										(3,0) -> o, (3,1) -> x, (3,2) -> x, (3,3) -> x))
	// 	testGame.isFinished()
	// }
	// test("Winner/iSfinish DiagTestDim = 4 first"){
	// 	val testGame = createGame(x, 4, Map((0,0) -> x,	(0,1) -> o, (0,2) -> x,	(0,3) -> o,
	// 										(1,0) -> o, (1,1) -> x, (1,2) -> o,	(1,3) -> x,
	// 										(2,0) -> x,	(2,1) -> o, (2,2) -> o,	(2,3) -> x,
	// 										(3,0) -> o, (3,1) -> x, (3,2) -> x, (3,3) -> x))
	// 	println(testGame.isFinished() + " " + testGame.getWinner())
	// }

	// test("Winner/isFinished DiagTestDim=4 sec"){
	// 	val testGame = createGame(x, 4, Map((0,0) -> x,	(0,1) -> o, (0,2) -> x,	(0,3) -> o,
	// 										(1,0) -> o, (1,1) -> x, (1,2) -> x,	(1,3) -> x,
	// 										(2,0) -> x,	(2,1) -> o, (2,2) -> o,	(2,3) -> x,
	// 										(3,0) -> o, (3,1) -> x, (3,2) -> x, (3,3) -> x))
	// 	println(testGame.isFinished() + " " + testGame.getWinner())
	// }
	test("NextBoards"){
		val testGame = createGame(x, 3, Map(			(0,1) -> o, (0,2) -> x,
											(1,0) -> x, (1,1) -> o, (1,2) -> o,
														(2,1) -> x, (2,2) -> x))
		println(testGame.nextBoards()(0).boardList + "\n" + testGame.nextBoards()(1).boardList)
	}

	// test("Minimax"){
	// 	val testGame = createGame(x, 3, Map(			(0,1) -> o, (0,2) -> x,
	// 										(1,0) -> x, (1,1) -> o, (1,2) -> o,
	// 													(2,1) -> x, (2,2) -> x))
	// 	Solution.minimax(testGame)
	// }
	// test("Minimaxtest"){
	// 	val testGame = createGame(x, 3, Map())
	// 	Solution.minimax(testGame)
	// }
	// test("randomassboard"){
	// 	val testGame = createGame(o, 4, Map((0,0) -> x,	(0,1) -> o, (0,2) -> x,	(0,3) -> o,
	// 													(1,1) -> x, (1,2) -> o,	(1,3) -> o,
	// 										(2,0) -> o,	(2,1) -> o,				(2,3) -> x,
	// 													(3,1) -> o, (3,2) -> x, (3,3) -> o))
	// 	println(Solution.minimax(testGame))
	// }
	// test("Minimax"){
	// 	val testGame = createGame(x, 3, Map((0,0) -> o,	(0,1) -> x,	(0,2) -> o,
	// 													(1,1) -> x,
	// 																(2,2) -> x))
	// 	println(Solution.minimax(testGame))
	// }
	// test("minimaxblank"){
	// 	val testGame = createGame(x, 3, Map())

	// 	println(Solution.minimax(testGame))
	// }
}