// Copyright 2015-2023 Nstream, inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package swim.dynamic.api.agent;

import swim.dynamic.HostPackage;
import swim.dynamic.JavaHostPackage;

public final class SwimApiAgent {

  private SwimApiAgent() {
    // static
  }

  public static final HostPackage PACKAGE;

  static {
    final JavaHostPackage hostPkg = new JavaHostPackage("swim.api.agent");
    PACKAGE = hostPkg;
    hostPkg.addHostType(HostAgent.TYPE);
    hostPkg.addHostType(HostAgentContext.TYPE);
    hostPkg.addHostType(HostAgentFactory.TYPE);
    hostPkg.addHostType(HostAgentRoute.TYPE);
    hostPkg.addHostType(HostAgentRouteContext.TYPE);
  }

}
