rhino-play
==========

Playing with Mozilla Rhino.

The main things I wanted to see was how easy is it to evaluate predicates which turned out to be trivial.
I also wanted to expose an object's methods as global level functions as some of the stackflow questions around this made it seem quite difficult.
For instance, [this post](http://stackoverflow.com/questions/2552300/how-can-i-add-methods-from-a-java-class-as-global-functions-in-javascript-using)


I ended up following the approach outlined in [this answer](http://stackoverflow.com/a/16479685/1089998), but I added a helper base class that allows me to just annotate the methods I wish to make available as a global function in javascript.    
```java
public class AnnotatedScriptableObject extends ScriptableObject {

	@Target(METHOD)
	@Retention(RUNTIME)
	public @interface Expose {
	}

	public void addToScope(Scriptable scope) {
		for (Method method : this.getClass().getMethods()) {
			if (method.isAnnotationPresent(Expose.class)) {
				FunctionObject function = new FunctionObject(method.getName(), method, this);
				scope.put(function.getFunctionName(), scope, function);
			}
		}
	}

	@Override
	public String getClassName() {
		return getClass().getName();
	}
}
```

So, my context object is simply: 

```java
public class Model extends AnnotatedScriptableObject {
	...
	@Expose
	public String nicknameOf(String friendsName) {
		return relativesNamesToNicknames.get(friendsName);
	}
	...
```
 
 An example of using this:


```java
	public static void main(String[] args) {

		Model data = new Model()//
				.addRelative("James", "Jim", "Uncle", 50)//
				.addRelative("Linda", "Aunty Linda", "Aunt", 48)//
				.addRelative("Nicholas", "Nick", "Cousin", 32);

		PredicateEvaluator evaluator = new PredicateEvaluator(data);

		try {

			checkState(evaluator.evaluate("nicknameOf('Linda') == 'Aunty Linda'"));
			checkState(evaluator.evaluate("nicknameOf('James') != 'Johnny'"));
			
			checkState(evaluator.evaluate("ageOf('James') == 50"));
			checkState(evaluator.evaluate("! (ageOf('Nicholas') >= 50)"));
			
			checkState(evaluator.evaluate("myRelationTo('Nicholas') == 'Cousin'"));
			checkState(evaluator.evaluate("myRelationTo('Linda') != 'Nemesis'"));
			
		} finally {
			
			evaluator.stop();
		}
	}
```
