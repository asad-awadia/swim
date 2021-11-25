// Copyright 2015-2021 Swim Inc.
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

import type {Timing} from "@swim/util";
import {Affinity, Animator} from "@swim/component";
import {AnyLength, Length} from "@swim/math";
import {AnyColor, Color} from "@swim/style";
import type {MoodVector, ThemeMatrix} from "@swim/theme";
import {ThemeAnimator} from "@swim/theme";
import {ViewContextType, ViewFlags, View} from "@swim/view";
import {HtmlViewInit, HtmlView} from "@swim/dom";
import type {Graphics} from "../graphics/Graphics";
import {Icon} from "./Icon";
import {FilledIcon} from "./FilledIcon";
import {IconViewInit, IconView} from "./IconView";
import {IconGraphicsAnimator} from "./IconGraphicsAnimator";
import {SvgIconView} from "./SvgIconView";

/** @public */
export interface HtmlIconViewInit extends HtmlViewInit, IconViewInit {
}

/** @public */
export class HtmlIconView extends HtmlView implements IconView {
  constructor(node: HTMLElement) {
    super(node);
    this.initIcon();
  }

  protected initIcon(): void {
    this.position.setState("relative", Affinity.Intrinsic);
    const svgView = this.createSvgView();
    if (svgView !== null) {
      this.setChild("svg", svgView);
    }
  }

  protected createSvgView(): SvgIconView | null {
    return SvgIconView.create();
  }

  get svgView(): SvgIconView | null {
    const svgView = this.getChild("svg");
    return svgView instanceof SvgIconView ? svgView : null;
  }

  @Animator({type: Number, state: 0.5, updateFlags: View.NeedsLayout})
  readonly xAlign!: Animator<this, number>;

  @Animator({type: Number, state: 0.5, updateFlags: View.NeedsLayout})
  readonly yAlign!: Animator<this, number>;

  @ThemeAnimator({type: Length, state: null, updateFlags: View.NeedsLayout})
  readonly iconWidth!: ThemeAnimator<this, Length | null, AnyLength | null>;

  @ThemeAnimator({type: Length, state: null, updateFlags: View.NeedsLayout})
  readonly iconHeight!: ThemeAnimator<this, Length | null, AnyLength | null>;

  @ThemeAnimator<HtmlIconView, Color | null, AnyColor | null>({
    type: Color,
    state: null,
    updateFlags: View.NeedsLayout,
    didSetValue(newIconColor: Color | null, oldIconColor: Color | null): void {
      if (newIconColor !== null) {
        const oldGraphics = this.owner.graphics.value;
        if (oldGraphics instanceof FilledIcon) {
          const newGraphics = oldGraphics.withFillColor(newIconColor);
          this.owner.graphics.setState(newGraphics, Affinity.Reflexive);
        }
      }
    },
  })
  readonly iconColor!: ThemeAnimator<this, Color | null, AnyColor | null>;

  @ThemeAnimator({extends: IconGraphicsAnimator, type: Object, state: null, updateFlags: View.NeedsLayout})
  readonly graphics!: ThemeAnimator<this, Graphics | null>;

  protected override onInsertChild(child: View, target: View | null): void {
    super.onInsertChild(child, target);
    if (child.key === "svg" && child instanceof SvgIconView) {
      this.onInsertSvg(child);
    }
  }

  protected onInsertSvg(svgView: SvgIconView): void {
    svgView.xAlign.setInherits(true);
    svgView.yAlign.setInherits(true);
    svgView.iconWidth.setInherits(true);
    svgView.iconHeight.setInherits(true);
    svgView.iconColor.setInherits(true);
    svgView.graphics.setInherits(true);
    svgView.setStyle("position", "absolute");
  }

  protected override onApplyTheme(theme: ThemeMatrix, mood: MoodVector, timing: Timing | boolean): void {
    super.onApplyTheme(theme, mood, timing);
    if (!this.graphics.inherited) {
      const oldGraphics = this.graphics.value;
      if (oldGraphics instanceof Icon) {
        const newGraphics = oldGraphics.withTheme(theme, mood);
        this.graphics.setState(newGraphics, oldGraphics.isThemed() ? timing : false, Affinity.Reflexive);
      }
    }
  }

  protected override onResize(viewContext: ViewContextType<this>): void {
    super.onResize(viewContext);
    this.requireUpdate(View.NeedsLayout);
  }

  protected override needsDisplay(displayFlags: ViewFlags, viewContext: ViewContextType<this>): ViewFlags {
    if ((this.flags & View.NeedsLayout) === 0) {
      displayFlags &= ~View.NeedsLayout;
    }
    return displayFlags;
  }

  protected override onLayout(viewContext: ViewContextType<this>): void {
    super.onLayout(viewContext);
    this.layoutIcon();
  }

  protected layoutIcon(): void {
    const svgView = this.svgView;
    if (svgView !== null && (svgView.width.hasAffinity(Affinity.Intrinsic)
                          || svgView.height.hasAffinity(Affinity.Intrinsic)
                          || svgView.viewBox.hasAffinity(Affinity.Intrinsic))) {
      let viewWidth: Length | number | null = this.width.value;
      viewWidth = viewWidth instanceof Length ? viewWidth.pxValue() : this.node.offsetWidth;
      let viewHeight: Length | number | null = this.height.value;
      viewHeight = viewHeight instanceof Length ? viewHeight.pxValue() : this.node.offsetHeight;
      svgView.width.setState(viewWidth, Affinity.Intrinsic);
      svgView.height.setState(viewHeight, Affinity.Intrinsic);
      svgView.viewBox.setState("0 0 " + viewWidth + " " + viewHeight, Affinity.Intrinsic);
    }
  }

  override init(init: HtmlIconViewInit): void {
    super.init(init);
    IconView.init(this, init);
  }
}
