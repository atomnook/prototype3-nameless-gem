package domain

import model.tactics.NumericOperator
import model.tactics.NumericOperator.{EQUAL_TO, GREATER_THAN, GREATER_THAN_EQUAL_TO, LESS_THAN, LESS_THAN_EQUAL_TO, NOT_EQUAL_TO, Unrecognized}



package object label {
  private[this] def unrecognized(value: Int): String = s"unrecognized($value)"

  implicit class NumericOperatorSign(operator: NumericOperator) {
    def sign: String = {
      operator match {
        case EQUAL_TO => "="
        case NOT_EQUAL_TO => "!="
        case LESS_THAN => "<"
        case LESS_THAN_EQUAL_TO => "<="
        case GREATER_THAN => ">"
        case GREATER_THAN_EQUAL_TO => ">="
        case Unrecognized(value) => unrecognized(value)
      }
    }
  }
}
