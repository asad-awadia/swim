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

package swim.dataflow.operator;

import swim.streamlet.AbstractOutlet;
import swim.streamlet.Inlet;
import swim.streamlet.Outlet;
import swim.streamlet.OutletInlet;
import swim.structure.Item;
import swim.structure.Value;

public abstract class BinaryOutlet extends AbstractOutlet<Value> {

  final Inlet<Value> lhsInlet;
  final Inlet<Value> rhsInlet;

  public BinaryOutlet() {
    this.lhsInlet = new OutletInlet<Value>(this);
    this.rhsInlet = new OutletInlet<Value>(this);
  }

  public Inlet<Value> lhsInlet() {
    return this.lhsInlet;
  }

  public Inlet<Value> rhsInlet() {
    return this.rhsInlet;
  }

  @Override
  public Value get() {
    final Outlet<? extends Value> lhsInput = this.lhsInlet.input();
    final Outlet<? extends Value> rhsInput = this.rhsInlet.input();
    if (lhsInput != null && rhsInput != null) {
      final Value lhs = lhsInput.get();
      final Value rhs = rhsInput.get();
      if (lhs != null && rhs != null) {
        final Item result = this.evaluate(lhs, rhs);
        return result.toValue();
      }
    }
    return Value.absent();
  }

  protected abstract Item evaluate(Value lhs, Value rhs);

}
