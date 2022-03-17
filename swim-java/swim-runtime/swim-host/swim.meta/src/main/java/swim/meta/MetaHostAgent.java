// Copyright 2015-2022 Swim.inc
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

package swim.meta;

import swim.system.HostBinding;
import swim.system.NodeContext;
import swim.system.agent.AgentNode;

public final class MetaHostAgent extends AgentNode {

  protected final HostBinding host;

  public MetaHostAgent(HostBinding host) {
    this.host = host;
  }

  @Override
  public void setNodeContext(NodeContext nodeContext) {
    super.setNodeContext(nodeContext);
    this.host.openMetaHost(this.host, this);
  }

}