package json

import model.{Employee}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

object JsonHelper {
  implicit val employeeEncoder: JsonEncoder[Employee] =
    DeriveJsonEncoder.gen[Employee]

  implicit val employeeDecoder: JsonDecoder[Employee] =
    DeriveJsonDecoder.gen[Employee]
}
