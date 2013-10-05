package uk.co.optimisticpanda.rhino;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

public class PredicateEvaluator {

	private Context context;
	private ScriptableObject scope;

	public PredicateEvaluator(AnnotatedScriptableObject... objects) {
		context = Context.enter();
		scope = context.initStandardObjects();
		for (AnnotatedScriptableObject object : objects) {
			object.addToScope(scope);
		}
	}

	public boolean evaluate(String rule) {
		Object result = context.evaluateString(scope, rule, "<rule>", 1, null);
		return Context.toBoolean(result);
	}

	public void stop() {
		Context.exit();
	}
	
}
