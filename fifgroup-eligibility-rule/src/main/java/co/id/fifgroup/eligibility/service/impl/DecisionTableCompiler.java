package co.id.fifgroup.eligibility.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*import org.drools.compiler.lang.DrlDumper;
import org.drools.compiler.lang.api.CEDescrBuilder;
import org.drools.compiler.lang.api.DescrFactory;
import org.drools.compiler.lang.api.PackageDescrBuilder;
import org.drools.compiler.lang.api.PatternDescrBuilder;
import org.drools.compiler.lang.api.RuleDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;*/
import org.drools.lang.DrlDumper;
import org.drools.lang.api.CEDescrBuilder;
import org.drools.lang.api.DescrFactory;
import org.drools.lang.api.PackageDescrBuilder;
import org.drools.lang.api.PatternDescrBuilder;
import org.drools.lang.api.RuleDescrBuilder;
import org.drools.lang.descr.AndDescr;

import com.google.common.base.Strings;

import co.id.fifgroup.eligibility.constant.FieldType;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;
import co.id.fifgroup.eligibility.dto.Fact;
import co.id.fifgroup.eligibility.dto.Result;

public class DecisionTableCompiler {

	private DrlDumper dumper = new DrlDumper();
	
	private int countNonEmptyCondition(String[] tokens) {
		int result = 0;
		for (String token : tokens ) 
			if (!token.trim().equals(""))
				result++;
		return result;
	}
	
	public String compile(DecisionTableDTO decisionTable) {
		
		DecisionTableModelDTO model = decisionTable.getModel();
		
		PackageDescrBuilder builder = DescrFactory.newPackage();
		builder.name(decisionTable.getModel().getId());
		builder.newImport().target(Fact.class.getName()).end();
		builder.newImport().target(Result.class.getName()).end();
		builder.newImport().target(Date.class.getName()).end();
		builder.newImport().target(BigDecimal.class.getName()).end();
		builder.newGlobal().identifier("results").type("co.id.fifgroup.eligibility.dto.Results").end();
		
		int rowSequence = 0;
		for (DecisionTableRowDTO row : decisionTable.getRows()) {
			String[] conditionToken = row.getConditions().split("\\|");
			RuleDescrBuilder ruleBuilder = builder.newRule().name(model.getName() + rowSequence++);
			Integer salience = (1000 - rowSequence) + 1000 * countNonEmptyCondition(conditionToken);
			ruleBuilder.attribute("salience", "" + salience );
			CEDescrBuilder<RuleDescrBuilder, AndDescr> conditions =ruleBuilder.lhs();
			
			for (String factTypeId : model.getFactTypes()) {
				String factId = "$" + factTypeId;
				PatternDescrBuilder<CEDescrBuilder<RuleDescrBuilder, AndDescr>> pattern = conditions.pattern("Fact")
						.id(factId, false);
				pattern.constraint(String.format("name == \"%s\"", factTypeId));
				Map<String, String> dateFields = new HashMap<>();
				int index = 0;
				for (DecisionTableColumnDTO column : model.getConditions()) {
					if (conditionToken.length > index 
							&& !conditionToken[index].trim().equals("")
							&& factTypeId.equals(column.getFactType().getId())) {
						if (column.getField().getFieldType() != FieldType.DATE) {
							pattern.constraint(String.format("properties[\"%s\"] %s \"%s\""
										, column.getField().getName()
										, column.getOperator().getSymbol()
										, conditionToken[index].trim()
										));
						} else {
							dateFields.put(String.format("this %s \"%s\"", column.getOperator().getSymbol(), conditionToken[index].trim() ), 
									String.format("%s.properties[\"%s\"]", factId, column.getField().getName()));
						}
					}
					index++;
				}
				pattern.end();
				for (String key : dateFields.keySet()) {
					PatternDescrBuilder<CEDescrBuilder<RuleDescrBuilder, AndDescr>> datePattern = conditions.pattern("Date");
					datePattern.constraint(key)
						.from()
						.expression(dateFields.get(key))
						.end();
				}
			}
			conditions.end();
			StringBuilder sb = new StringBuilder();
			sb.append("    ").append("Result result = new Result(" + salience + ");").append("\n");
			String[] resultToken = row.getResults().toString().split("\\|");
			int resultIndex = 0;
			for (DecisionTableColumnDTO column : model.getResults()) {
				if (resultToken.length > resultIndex 
						&& !Strings.isNullOrEmpty(resultToken[resultIndex].trim())) {
					sb.append("    ").append("result.getProperties().put(\"");
					sb.append(column.getField().getName());
					sb.append("\",");
					switch (column.getField().getFieldType()) {
						case NUMBER:
							sb.append("new BigDecimal(\"");
							String val = resultToken[resultIndex].trim();
							sb.append(Strings.isNullOrEmpty(val) ? "0" : val);
							sb.append("\")");
							break;
						case TEXT:
							sb.append("\"");
							sb.append(resultToken[resultIndex].trim());
							sb.append("\"");
							break;
						case LOOKUP:
							sb.append("\"");
							sb.append(resultToken[resultIndex].trim());
							sb.append("\"");
							break;
						default:
							break;
					}
					sb.append(");").append("\n");
				}
				resultIndex++;
			}
			sb.append("    ").append("results.add(result);").append("\n");
			ruleBuilder.rhs(sb.toString())
				.end();
		}
		
		return dumper.dump(builder.getDescr());
	}
}

