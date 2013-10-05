package uk.co.optimisticpanda.rhino;

import java.util.Map;

import com.google.common.collect.Maps;

@SuppressWarnings("serial")
public class Model extends AnnotatedScriptableObject {

	private Map<String, String> relativesNamesToNicknames = Maps.newHashMap();
	private Map<String, String> relativesNamesToRelationship = Maps.newHashMap();
	private Map<String, Integer> relativesNamesToAge = Maps.newHashMap();

	@Expose
	public String nickNameOf(String friendsName) {
		return relativesNamesToNicknames.get(friendsName);
	}

	@Expose
	public String myRelationTo(String friendsName) {
		return relativesNamesToRelationship.get(friendsName);
	}

	@Expose
	public int ageOf(String friendsName) {
		if (!relativesNamesToAge.containsKey(friendsName)) {
			return 0;
		}
		return relativesNamesToAge.get(friendsName).intValue();
	}

	public Model addRelative(String name, String nickname, String relationship, int age) {
		relativesNamesToAge.put(name, Integer.valueOf(age));
		relativesNamesToNicknames.put(name, nickname);
		relativesNamesToRelationship.put(name, relationship);
		return this;
	}

}
