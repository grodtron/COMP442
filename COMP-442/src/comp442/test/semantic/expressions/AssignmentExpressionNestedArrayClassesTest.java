package comp442.test.semantic.expressions;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import comp442.codegen.Register;
import comp442.semantic.expressions.AssignmentExpression;
import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.StaticIntValue;
import comp442.semantic.value.StoredValue;
import comp442.semantic.value.Value;
import comp442.syntactical.parser.Parser;

@RunWith(Parameterized.class)
public class AssignmentExpressionNestedArrayClassesTest {

	private final static String skeleton = ""
			+ "class A {\n"	// size = 4
			+ "	int id;\n"	// offset 0
			+ "};\n"
			+ "class B{\n"	// size = 12 + 4 = 16
			+ "	A a[3];\n"	// offset 0
			+ "	int id;\n"	// offset 3*4 = 12
			+ "};\n"
			+ "class C{\n"	// size = 48 + 4 = 52
			+ "	B b[3];\n"	// offset 0
			+ "	int id;\n"	// offset 3*(12 + 4) = 48
			+ "};\n"
			+ "class D{\n"	// size = 156 + 4 = 160
			+ "	C c[3];\n"	// offset 0
			+ "	int id;\n"	// offset 3*(48 + 4) = 156
			+ "};\n"
			+ "program {\n"
			+ "	D d[3];\n"	// offset 0
			+ "	int x;\n"	// offset 160*3
			+ "	%s;\n"
			+ "};\n";
	
	private final static int Dsize = 160;
	private final static int Csize = 52;
	private final static int Bsize = 16;
	private final static int Asize = 4;
	
	private static class Var {
		public final String code;
		public final Value  expected;
		
		public Var(String code, Value expected){
			this.code     = code;
			this.expected = expected;
		}
	}
	
	@Parameters(name="{0}")
	public static Collection<Object[]> data(){
		List<Var> vars = new ArrayList<AssignmentExpressionNestedArrayClassesTest.Var>( 2*2*(3*3*3)*(3*3*3)*5 ); // estimate of size
		
		for(int i = 0; i < (3*3*3*3); ++i){
			int a = i%3;
			int b = (i/3)%3;
			int c = ((i/3)/3)%3;
			int d = (((i/3)/3)/3)%3;
			
			String D = "d[" + (d+1) + "].";
			String C = "c[" + (c+1) + "].";
			String B = "b[" + (b+1) + "].";
			String A = "a[" + (a+1) + "].";
			
			vars.add(new Var( D+C+B+A + "id", new StoredValue(new RegisterValue(Register.STACK_POINTER),
					new StaticIntValue( 0 // d offset
							+ (d*Dsize) + (c*Csize) + (b*Bsize) + (a*Asize) + (Asize - 4)
							)) ));
			if(a == 0){
				vars.add(new Var( D+C+B + "id", new StoredValue(new RegisterValue(Register.STACK_POINTER),
						new StaticIntValue( 0 // d offset
								+ (d*Dsize) + (c*Csize) + (b*Bsize) + (Bsize - 4)
								)) ));
				if(b == 0){
					vars.add(new Var( D+C + "id", new StoredValue(new RegisterValue(Register.STACK_POINTER),
							new StaticIntValue( 0 // d offset
									+ (d*Dsize) + (c*Csize) + (Csize - 4)
									)) ));
					if(c == 0){
						vars.add(new Var( D + "id", new StoredValue(new RegisterValue(Register.STACK_POINTER),
								new StaticIntValue( 0 // d offset
										+ (d*Dsize) + (Dsize - 4)
										)) ));
					}
				}
			}
			
		}
		
		List<Object[]> returnVal = new ArrayList<Object[]>();
		
		// almost 20,000 tests, takes bit too long....
//		for(List<Var> v : Permutations.permutations(vars.toArray(new Var[ vars.size() ]), 2)){
//			String assignmentExpression = v.get(0).code + " = " + v.get(1).code;
//			
//			Value lhs = v.get(0).expected;
//			Value rhs = v.get(1).expected;
//			
//			returnVal.add(new Object[]{ assignmentExpression, String.format(skeleton, assignmentExpression), lhs, rhs });
//		}
		
		Var x = new Var("x", new StoredValue(new RegisterValue(Register.STACK_POINTER), new StaticIntValue(160*3)));
		
		for(Var v : vars){
			{
				Value lhs = v.expected;
				Value rhs = x.expected;
	
				String assignmentExpression = v.code + " = " + x.code;
				
				returnVal.add(new Object[]{ assignmentExpression, String.format(skeleton, assignmentExpression), lhs, rhs });
			}
			{
				Value lhs = x.expected;
				Value rhs = v.expected;
	
				String assignmentExpression = x.code + " = " + v.code;
				
				returnVal.add(new Object[]{ assignmentExpression, String.format(skeleton, assignmentExpression), lhs, rhs });
			}
		}

		return returnVal;
		
	}

	private Parser parser;
	private Value lhs;
	private Value rhs;
	
	public AssignmentExpressionNestedArrayClassesTest(String name, String code, Value lhs, Value rhs ) {
		this.parser = new Parser(new ByteArrayInputStream(code.getBytes()));
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	@Test
	public void test() {
		parser.parse();
		
		FunctionEntry program =  (FunctionEntry) SymbolContext.getCurrentScope().find("program");
		
		List<Statement> statements = program.getStatements();
		
		assertEquals(1, statements.size());
		
		AssignmentExpression s = (AssignmentExpression)statements.get(0);
				
		assertEquals(lhs, s.getLhs());
		assertEquals(rhs, s.getRhs());
	}

}
