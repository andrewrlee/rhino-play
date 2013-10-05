package uk.co.optimisticpanda.rhino;
import static com.google.common.base.Preconditions.checkState;

public class RhinoPlay {

	public static void main(String[] args) {

		Model data = new Model()//
				.addRelative("James", "Jim", "Uncle", 50)//
				.addRelative("Linda", "Aunty Linda", "Aunt", 48)//
				.addRelative("Nicholas", "Nick", "Cousin", 32);

		PredicateEvaluator evaluator = new PredicateEvaluator(data);

		try {

			checkState(evaluator.evaluate("nickNameOf('Linda') == 'Aunty Linda'"));
			checkState(evaluator.evaluate("nickNameOf('James') != 'Johnny'"));
			
			checkState(evaluator.evaluate("ageOf('James') == 50"));
			checkState(evaluator.evaluate("ageOf('Nicholas') != 50"));
			
			checkState(evaluator.evaluate("myRelationTo('Nicholas') == 'Cousin'"));
			checkState(evaluator.evaluate("myRelationTo('Linda') != 'Nemesis'"));
			
		} finally {
			
			evaluator.stop();
		}
	}
}
