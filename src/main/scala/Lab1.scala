object Lab1 extends jsy.util.JsyApplication {
  import jsy.lab1.ast._
  import jsy.lab1.Parser
  
  /*
   * CSCI 3155: Lab 1
   * Ryan Denzel
   * 
   * Partner: Matt Binsfeld
   * Collaborators: <Any Collaborators>
   */

  /*
   * Fill in the appropriate portions above by replacing things delimited
   * by '<'... '>'.
   * 
   * Replace the 'throw new UnsupportedOperationException' expression with
   * your code in each function.
   * 
   * Do not make other modifications to this template, such as
   * - adding "extends App" or "extends Application" to your Lab object,
   * - adding a "main" method, and
   * - leaving any failing asserts.
   * 
   * Your lab will not be graded if it does not compile.
   * 
   * This template compiles without error. Before you submit comment out any
   * code that does not compile or causes a failing assert.  Simply put in a
   * 'throws new UnsupportedOperationException' as needed to get something
   * that compiles without error.
   */
  
  /*
   * Example with a Unit Test
   * 
   * A convenient, quick-and-dirty way to experiment, especially with small code
   * fragments, is to use the interactive Scala interpreter.
   * 
   * To run a selection in the interpreter in Eclipse, highlight the code of interest
   * and type Ctrl+Shift+X (on Windows) or Cmd+Shift+X (on Mac).
   * 
   * Highlight the next few lines below to try it out.  The assertion passes, so
   * it appears that nothing happens.  You can uncomment the "bad test specification"
   * and see that a failed assert throws an exception.
   * 
   * You can try calling 'plus' with some arguments, for example, plus(1,2).  You
   * should get a result something like 'res0: Int = 3'.
   * 
   * As an alternative, the testPlus2 function takes an argument that has the form
   * of a plus function, so we can try it with different implementations.  For example,
   * uncomment the "testPlus2(badplus)" line, and you will see an assertion failure.
   * 
   * Our convention is that these "test" functions are testing code that are not part
   * of the "production" code.
   * 
   * While writing such testing snippets are convenient, it is not ideal.  For example,
   * the 'testPlus1()' call is run whenever this object is loaded, so in practice,
   * it should probably be deleted for "release".  A more robust way to maintain
   * unit tests is in a separate file.  For us, we use the convention of writing
   * tests in a file called LabXSpec.scala (i.e., Lab1Spec.scala for Lab 1).
   */
  
  def plus(x: Int, y: Int): Int = x + y
  def testPlus1() {
    assert(plus(1,1) == 2)
    //assert(plus(1,1) == 3) // bad test specification
  }
  testPlus1()

  def badplus(x: Int, y: Int): Int = x - y
  def testPlus2(plus: (Int, Int) => Int) {
    assert(plus(1,1) == 2)
  }
  //testPlus2(badplus)

  /* Exercises */

  def abs(n: Double): Double = {//could square then take root
    if(n < 0) n * (-1); else n
  } 

  def xor(a: Boolean, b: Boolean): Boolean = {
   if(a==b) false
   else true
  }

  
  def repeat(s: String, n: Int): String = {
    require(n >= 0)
    val orig = s
    if (n == 0){
      ""
    }
    else{
    	repeat_helper(s, orig, n-1)
    }
  }
  def repeat_helper(s: String, orig: String, n: Int):String ={
    if(n > 0) repeat_helper(s + orig, orig, n - 1)
    else s
  }
  
  def sqrtStep(c: Double, xn: Double): Double = {
    xn - (((xn*xn)-c)/(2*xn))
  }

  def sqrtN(c: Double, x0: Double, n: Int): Double = {
		require(c >= 0)
		require(n >= 0)
		sqrtN_helper(c, x0, n, 0)
  }
  def sqrtN_helper(c: Double, x0: Double, n: Int, iter: Int): Double = {
	  if(iter < n) {var temp = sqrtStep(c, x0)
	  sqrtN_helper(c, temp, n, iter + 1)}
	  else x0
  }
  
  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double ={
    require(c >= 0, "c is negative")
    require(epsilon > 0)
    if (abs((x0*x0)-c) >= epsilon){
      val temp = sqrtStep(c, x0)
      sqrtErr(c, temp, epsilon)
    }else x0
  }
  def sqrt(c: Double): Double = {
    require(c >= 0)
    if (c == 0) 0 else sqrtErr(c, 1.0, 0.0001)
  }
  
  /* Search Tree */
  
  sealed abstract class SearchTree
  case object Empty extends SearchTree
  case class Node(l: SearchTree, d: Int, r: SearchTree) extends SearchTree
  
  def repOk(t: SearchTree): Boolean = {
    def check(t: SearchTree, min: Int, max: Int): Boolean = t match {
      case Empty => true//yay we be done
      case Node(l, d, r) => min <= d && d < max && 
      check(l, min, d) && check(r, d, max)
      						
    }
    check(t, Int.MinValue, Int.MaxValue)
  }
  
  def insert(t: SearchTree, n: Int): SearchTree = t match {
    case Node(l, d, r) => if(d > n) Node(insert(l, n), d, r)
    					else Node(l, d, insert(r, n))
    case(Empty) => Node(Empty, n, Empty)
  }
  
  def deleteMin(t: SearchTree): (SearchTree, Int) = {
    require(t != Empty)
    (t: @unchecked) match {
      case Node(Empty, d, r) => (r, d)
      case Node(l, d, r) =>
        val (l1, m) = deleteMin(l)
        return (Node(l1, d, r), m)
    }
  }
 
  def delete(t: SearchTree, n: Int): SearchTree = t match{
    case Empty => throw new UnsupportedOperationException
  	case Node(l, d, r) => val n1 = n 
  			if(n < d) Node(delete(l, n), d, r)
    		else if(d == n)
    		  t match{
    		  case Node(l, d1, Node(Empty, n, Empty)) => Node(l, d1, Empty)
    		  case Node(l, d1, Node(l1, n, Empty)) => Node(l, d1, l1)
    		  case Node(l, d1, Node(Empty, n, r1)) => Node(Empty, d1, r1)
    		  case Node(l, d1, Node(l1, n, r1)) => throw new UnsupportedOperationException
    		  case Node(Node(Empty, n, Empty), d1, r) => Node(Empty, d1, r)
    		  case Node(Node(l1, n, Empty), d1, r) => Node(l1, d1, r)
    		  case Node(Node(Empty, n, r1), d1, r) => Node(r1, d1, r)
    		  case Node(Node(l1, n, r1), d1, r) => throw new UnsupportedOperationException
    		}
    		else Node(l, d, delete(r, n))
  	
  }
  
  /* JavaScripty */
  
  def eval(e: Expr): Double = e match {
    case N(n) => n
    case Unary(Neg, e1) => eval(pretty(e1)) - 2*eval(pretty(e1))
    case Binary(Plus, e1, e2) => eval(pretty(e1)) + eval(pretty(e2))
    case Binary(Minus, e1, e2) => eval(pretty(e1)) - eval(pretty(e2))
    case Binary(Times, e1, e2) => eval(pretty(e1)) * eval(pretty(e2))
    case Binary(Div, Unary(Neg, e1), N(0)) => Double.NegativeInfinity
    case Binary(Div, e1, N(0)) => Double.PositiveInfinity
    case Binary(Div, e1, e2) => eval(pretty(e1)) / eval(pretty(e2))
    case _ => throw new UnsupportedOperationException//added in after submitting
    
  }
  
 // Interface to run your interpreter from a string.  This is convenient
 // for unit testing.
 def eval(s: String): Double = eval(Parser.parse(s))



 /* Interface to run your interpreter from the command-line.  You can ignore the code below. */ 
  
 def processFile(file: java.io.File) {
    if (debug) { println("Parsing ...") }
    
    val expr = Parser.parseFile(file)
    
    if (debug) {
      println("\nExpression AST:\n  " + expr)
      println("------------------------------------------------------------")
    }
    
    if (debug) { println("Evaluating ...") }
    
    val v = eval(expr)
    
    println(v)
  }

}
