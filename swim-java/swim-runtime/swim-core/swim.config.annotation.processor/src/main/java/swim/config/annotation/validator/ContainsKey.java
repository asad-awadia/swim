package swim.config.annotation.validator;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface ContainsKey {
  String[] value();

  class Generator extends AbstractValidationGenerator<ContainsKey> {

    public Generator() {
      super(ContainsKey.class);
    }

    @Override
    protected String doc(ContainsKey attribute) {
      return String.format("Must contain '%s'", String.join("', '", attribute.value()));
    }

    @Override
    protected void addValidation(BlockStmt validateBody, FieldAccessExpr fieldAccessExpr, StringLiteralExpr methodNameLiteral, ContainsKey annotation) {
      BlockStmt containsChecks = new BlockStmt();
      IfStmt ifNull = errorIfNull(validateBody, fieldAccessExpr, methodNameLiteral)
          .setElseStmt(containsChecks);

      for (String key : annotation.value()) {
        MethodCallExpr containsCall = new MethodCallExpr()
            .setScope(fieldAccessExpr)
            .setName("containsKey")
            .addArgument(new StringLiteralExpr(key));

        IfStmt ifContains = new IfStmt()
            .setCondition(
                new UnaryExpr(
                    containsCall,
                    UnaryExpr.Operator.LOGICAL_COMPLEMENT
                )
            )
            .setThenStmt(
                new BlockStmt().addStatement(
                    addError(methodNameLiteral, fieldAccessExpr, "value must contain '%s'", key)
                )
            );
        containsChecks.addStatement(ifContains);
      }
    }
  }
}