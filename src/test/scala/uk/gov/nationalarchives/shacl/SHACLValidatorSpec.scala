package uk.gov.nationalarchives.shacl

import org.scalatest.TryValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SHACLValidatorSpec extends AnyWordSpec with Matchers with TryValues {

  "The isValid function" must {
    val shapeUri = getClass.getResource("/" + "ODRL-shape.ttl")
    val validator = SHACLValidator(shapeUri)
    "return true when the data graph conforms to the shapes graph" in {
      validator.isValid(getClass.getResource("/" + "ADM_354_168_299-policy.ttl")) mustBe true
    }
    "return false when the data graph conforms to the shapes graph" in {
      validator.isValid(getClass.getResource("/" + "ADM_354_168_299-policy-invalid.ttl")) mustBe false
    }
  }

}
