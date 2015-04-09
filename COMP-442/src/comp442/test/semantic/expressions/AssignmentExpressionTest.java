package comp442.test.semantic.expressions;

import static org.junit.Assert.*;

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
import comp442.utils.Permutations;

@RunWith(Parameterized.class)
public class AssignmentExpressionTest {

	private final static String skeleton = ""
			+ "class Inner {\n"
			+ "	int x;\n"						// offset 0
			+ "	int y;\n"						// offset 4
			+ "};\n"
			+ "class Outer {\n"
			+ "	int id;\n"						// offset 0
			+ "	Inner inner;\n"					// offset 4
			+ "};\n"
			+ "program {\n"
			+ "	int x;\n"						// offset 0
			+ "	int arr[3][3][3];\n"			// offset 4
			+ "	Inner inner;\n"					// offset 4 + 4*(3*3*3) = 112
			+ "	Outer outer;\n"					// offset 112 + 8 = 120
			+ "	Inner innerArr[3][3][3];\n"		// offset 120 + 12 = 132
			+ "	Outer outerArr[3][3][3];\n"		// offset 132 + 8*(3*3*3) = 348
			+ "	\n"
			+ "	%s;\n"
			+ "};\n";
	
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
		List<Var> vars = new ArrayList<AssignmentExpressionTest.Var>( 2*2*(3*3*3)*(3*3*3)*5 ); // estimate of size
		
		for(int i = 0; i < (3*3*3); ++i){
			int a = i%3;
			int b = (i/3)%3;
			int c = ((i/3)/3)%3;
			
			String indices = "[" + (a+1) + "][" + (b+1) + "][" + (c+1) + "]";
			
			vars.add(new Var( "outerArr"+indices+".inner.x", new StoredValue(new RegisterValue(Register.STACK_POINTER),
					new StaticIntValue( 348 + (a*12) + (3*12*b) + (3*3*c*12) + 4 + 0 )) ));

			vars.add(new Var( "outerArr"+indices+".inner.y", new StoredValue(new RegisterValue(Register.STACK_POINTER),
					new StaticIntValue( 348 + (a*12) + (3*12*b) + (3*3*c*12) + 4 + 4 )) ));
			
			vars.add(new Var( "outerArr"+indices+".id", new StoredValue(new RegisterValue(Register.STACK_POINTER),
					new StaticIntValue( 348 + (a*12) + (3*12*b) + (3*3*c*12) + 0 )) ));
			
			vars.add(new Var( "innerArr"+indices+".x", new StoredValue(new RegisterValue(Register.STACK_POINTER),
					new StaticIntValue( 132 + (a*8) + (3*8*b) + (3*3*c*8) + 0 )) ));

			vars.add(new Var( "innerArr"+indices+".y", new StoredValue(new RegisterValue(Register.STACK_POINTER),
					new StaticIntValue( 132 + (a*8) + (3*8*b) + (3*3*c*8) + 4 )) ));
			
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
		
		Var x = new Var("x", new StoredValue(new RegisterValue(Register.STACK_POINTER), new StaticIntValue(0)));
		
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
	
	public AssignmentExpressionTest(String name, String code, Value lhs, Value rhs ) {
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
