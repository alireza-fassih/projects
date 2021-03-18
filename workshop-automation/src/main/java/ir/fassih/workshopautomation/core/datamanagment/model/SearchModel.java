package ir.fassih.workshopautomation.core.datamanagment.model;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchModel {

	public enum SearchType {
		EQ, NE, LT, GT, LE, GE, LIKE;
		
		public String getPrefix() {
			return new StringBuilder().append( name() ).append( ":" ).toString();
		}
	}

	private Map<String, String> filters = new LinkedHashMap<>();

	@Builder.Default
	private int pageSize = 20;

    @Builder.Default
	private int page = 1;

}
