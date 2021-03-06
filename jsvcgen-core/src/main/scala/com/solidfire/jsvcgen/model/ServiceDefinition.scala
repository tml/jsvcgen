/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/
package com.solidfire.jsvcgen.model

import com.solidfire.jsvcgen.loader.JsvcgenDescription.TypeOrdinal
import com.solidfire.jsvcgen.model.ReleaseProcess.{ INTERNAL, StabilityLevel }

case class ServiceDefinition( serviceName:    String,
                              host:           String,
                              endpoint:       String,
                              types:          List[TypeDefinition],
                              methods:        List[Method],
                              schemes:        List[String]            = List("http"),
                              documentation:  Option[Documentation]   = None,
                              release:        StabilityLevel          = INTERNAL,
                              version:        String                  = "1.0",
                              isInterface:    Boolean                 = false,
                              typeOrdinality: List[TypeOrdinal]       = List()
                            ) {
  def asInterface () = this.copy (serviceName = serviceName + "IF", isInterface = true)
}