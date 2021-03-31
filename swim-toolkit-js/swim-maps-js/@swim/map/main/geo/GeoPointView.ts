// Copyright 2015-2020 Swim inc.
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

import type {AnyTiming} from "@swim/mapping";
import {AnyLength, Length, AnyPointR2, PointR2, BoxR2} from "@swim/math";
import {AnyGeoPoint, GeoPointInit, GeoPointTuple, GeoPoint, GeoBox} from "@swim/geo";
import {AnyFont, Font, AnyColor, Color} from "@swim/style";
import {ViewContextType, ViewFlags, View, ViewProperty, ViewAnimator, ViewFastener} from "@swim/view";
import {
  GraphicsView,
  TypesetView,
  AnyTextRunView,
  TextRunView,
  CanvasContext,
  CanvasRenderer,
} from "@swim/graphics";
import type {MapGraphicsViewController} from "../graphics/MapGraphicsViewController";
import {MapLayerView} from "../layer/MapLayerView";
import type {GeoViewInit, GeoView} from "./GeoView";
import type {GeoPointViewObserver} from "./GeoPointViewObserver";

export type GeoPointLabelPlacement = "auto" | "top" | "right" | "bottom" | "left";

export type AnyGeoPointView = GeoPointView | GeoPointViewInit | GeoPoint | GeoPointInit | GeoPointTuple;

export interface GeoPointViewInit extends GeoViewInit {
  lng?: number;
  lat?: number;
  x?: number;
  y?: number;

  radius?: AnyLength;

  hitRadius?: number;

  color?: AnyColor;
  opacity?: number;

  labelPadding?: AnyLength;
  labelPlacement?: GeoPointLabelPlacement;

  font?: AnyFont;
  textColor?: AnyColor;

  label?: GraphicsView | string;
}

export class GeoPointView extends MapLayerView implements GeoView {
  initView(init: GeoPointViewInit): void {
    super.initView(init);
    this.setState(init);
  }

  declare readonly viewController: MapGraphicsViewController<GeoPointView> & GeoPointViewObserver | null;

  declare readonly viewObservers: ReadonlyArray<GeoPointViewObserver>;

  protected willSetGeoPoint(newGeoPoint: GeoPoint, oldGeoPoint: GeoPoint): void {
    const viewController = this.viewController;
    if (viewController !== null && viewController.geoViewWillSetGeometry !== void 0) {
      viewController.geoViewWillSetGeometry(newGeoPoint, oldGeoPoint, this);
    }
    const viewObservers = this.viewObservers;
    for (let i = 0, n = viewObservers.length; i < n; i += 1) {
      const viewObserver = viewObservers[i]!;
      if (viewObserver.geoViewWillSetGeometry !== void 0) {
        viewObserver.geoViewWillSetGeometry(newGeoPoint, oldGeoPoint, this);
      }
    }
  }

  protected onSetGeoPoint(newGeoPoint: GeoPoint, oldGeoPoint: GeoPoint): void {
    this.updateGeoBounds(newGeoPoint);
  }

  protected didSetGeoPoint(newGeoPoint: GeoPoint, oldGeoPoint: GeoPoint): void {
    const viewObservers = this.viewObservers;
    for (let i = 0, n = viewObservers.length; i < n; i += 1) {
      const viewObserver = viewObservers[i]!;
      if (viewObserver.geoViewDidSetGeometry !== void 0) {
        viewObserver.geoViewDidSetGeometry(newGeoPoint, oldGeoPoint, this);
      }
    }
    const viewController = this.viewController;
    if (viewController !== null && viewController.geoViewDidSetGeometry !== void 0) {
      viewController.geoViewDidSetGeometry(newGeoPoint, oldGeoPoint, this);
    }
  }

  @ViewAnimator<GeoPointView, GeoPoint, AnyGeoPoint>({
    type: GeoPoint,
    state: GeoPoint.origin(),
    willSetValue(newGeoPoint: GeoPoint, oldGeoPoint: GeoPoint): void {
      this.owner.willSetGeoPoint(newGeoPoint, oldGeoPoint);
    },
    didSetValue(newGeoPoint: GeoPoint, oldGeoPoint: GeoPoint): void {
      this.owner.onSetGeoPoint(newGeoPoint, oldGeoPoint);
      this.owner.didSetGeoPoint(newGeoPoint, oldGeoPoint);
    },
  })
  declare geoPoint: ViewAnimator<this, GeoPoint, AnyGeoPoint>;

  @ViewAnimator({type: PointR2, state: PointR2.origin()})
  declare viewPoint: ViewAnimator<this, PointR2, AnyPointR2>;

  @ViewAnimator({type: Length, state: null})
  declare radius: ViewAnimator<this, Length | null, AnyLength | null>;

  @ViewAnimator({type: Color, state: null})
  declare color: ViewAnimator<this, Color | null, AnyColor | null>;

  @ViewAnimator({type: Number})
  declare opacity: ViewAnimator<this, number | undefined>;

  @ViewAnimator({type: Length, state: null})
  declare labelPadding: ViewAnimator<this, Length | null, AnyLength | null>;

  @ViewAnimator({type: Font, state: null, inherit: true})
  declare font: ViewAnimator<this, Font | null, AnyFont | null>;

  @ViewAnimator({type: Color, state: null, inherit: true})
  declare textColor: ViewAnimator<this, Color | null, AnyColor | null>;

  @ViewProperty({type: Number})
  declare hitRadius: ViewProperty<this, number | undefined>;

  protected initLabel(labelView: GraphicsView): void {
    // hook
  }

  protected attachLabel(labelView: GraphicsView): void {
    // hook
  }

  protected detachLabel(labelView: GraphicsView): void {
    // hook
  }

  protected willSetLabel(newLabelView: GraphicsView | null, oldLabelView: GraphicsView | null): void {
    const viewController = this.viewController;
    if (viewController !== null && viewController.geoViewWillSetLabel !== void 0) {
      viewController.geoViewWillSetLabel(newLabelView, oldLabelView, this);
    }
    const viewObservers = this.viewObservers;
    for (let i = 0, n = viewObservers.length; i < n; i += 1) {
      const viewObserver = viewObservers[i]!;
      if (viewObserver.geoViewWillSetLabel !== void 0) {
        viewObserver.geoViewWillSetLabel(newLabelView, oldLabelView, this);
      }
    }
  }

  protected onSetLabel(newLabelView: GraphicsView | null, oldLabelView: GraphicsView | null): void {
    if (oldLabelView !== null) {
      this.detachLabel(oldLabelView);
    }
    if (newLabelView !== null) {
      this.attachLabel(newLabelView);
      this.initLabel(newLabelView);
    }
  }

  protected didSetLabel(newLabelView: GraphicsView | null, oldLabelView: GraphicsView | null): void {
    const viewObservers = this.viewObservers;
    for (let i = 0, n = viewObservers.length; i < n; i += 1) {
      const viewObserver = viewObservers[i]!;
      if (viewObserver.geoViewDidSetLabel !== void 0) {
        viewObserver.geoViewDidSetLabel(newLabelView, oldLabelView, this);
      }
    }
    const viewController = this.viewController;
    if (viewController !== null && viewController.geoViewDidSetLabel !== void 0) {
      viewController.geoViewDidSetLabel(newLabelView, oldLabelView, this);
    }
  }

  @ViewFastener<GeoPointView, GraphicsView, AnyTextRunView>({
    key: true,
    type: TextRunView,
    fromAny(value: GraphicsView | AnyTextRunView): GraphicsView {
      if (value instanceof GraphicsView) {
        return value;
      } else if (typeof value === "string" && this.view instanceof TextRunView) {
        this.view.text(value);
        return this.view;
      } else {
        return TextRunView.fromAny(value);
      }
    },
    willSetView(newLabelView: GraphicsView | null, oldLabelView: GraphicsView | null): void {
      this.owner.willSetLabel(newLabelView, oldLabelView);
    },
    onSetView(newLabelView: GraphicsView | null, oldLabelView: GraphicsView | null): void {
      this.owner.onSetLabel(newLabelView, oldLabelView);
    },
    didSetView(newLabelView: GraphicsView | null, oldLabelView: GraphicsView | null): void {
      this.owner.didSetLabel(newLabelView, oldLabelView);
    },
  })
  declare label: ViewFastener<this, GraphicsView, AnyTextRunView>;

  @ViewProperty({type: String, state: "auto"})
  declare labelPlacement: ViewProperty<this, GeoPointLabelPlacement>;

  isGradientStop(): boolean {
    return this.color.value !== null || this.opacity.value !== void 0;
  }

  setState(point: AnyGeoPointView, timing?: AnyTiming | boolean): void {
    let init: GeoPointViewInit;
    if (point instanceof GeoPointView) {
      init = point.toAny();
    } else if (point instanceof GeoPoint) {
      init = point.toAny();
    } else if (GeoPoint.isTuple(point)) {
      init = {lng: point[0], lat: point[1]};
    } else {
      init = point;
    }
    if (init.lng !== void 0 && init.lat !== void 0) {
      this.geoPoint(new GeoPoint(init.lng, init.lat), timing);
    } else if (init.x !== void 0 && init.y !== void 0) {
      this.viewPoint(new PointR2(init.x, init.y), timing);
    }

    if (init.radius !== void 0) {
      this.radius(init.radius, timing);
    }

    if (init.hitRadius !== void 0) {
      this.hitRadius(init.hitRadius);
    }

    if (init.color !== void 0) {
      this.color(init.color, timing);
    }
    if (init.opacity !== void 0) {
      this.opacity(init.opacity, timing);
    }

    if (init.labelPadding !== void 0) {
      this.labelPadding(init.labelPadding, timing);
    }
    if (init.labelPlacement !== void 0) {
      this.labelPlacement(init.labelPlacement);
    }

    if (init.font !== void 0) {
      this.font(init.font, timing);
    }
    if (init.textColor !== void 0) {
      this.textColor(init.textColor, timing);
    }

    if (init.label !== void 0) {
      this.label(init.label);
    }
  }

  protected updateGeoBounds(geoPoint: GeoPoint): void {
    if (geoPoint.isDefined()) {
      const oldGeoBounds = this.geoBounds;
      const newGeoBounds = new GeoBox(geoPoint.lng, geoPoint.lat, geoPoint.lng, geoPoint.lat);
      if (!oldGeoBounds.equals(newGeoBounds)) {
        Object.defineProperty(this, "geoBounds", {
          value: newGeoBounds,
          enumerable: true,
          configurable: true,
        });
        this.didSetGeoBounds(newGeoBounds, oldGeoBounds);
        this.requireUpdate(View.NeedsProject);
      }
    }
  }

  needsProcess(processFlags: ViewFlags, viewContext: ViewContextType<this>): ViewFlags {
    if ((processFlags & View.NeedsProject) !== 0 && this.label.view !== null) {
      this.requireUpdate(View.NeedsLayout);
    }
    return processFlags;
  }

  protected onProject(viewContext: ViewContextType<this>): void {
    super.onProject(viewContext);
    if (this.viewPoint.isPrecedent(View.Intrinsic)) {
      const viewPoint = viewContext.geoProjection.project(this.geoPoint.getValue());
      //this.viewPoint.setState(viewPoint, View.Intrinsic);
      Object.defineProperty(this.viewPoint, "ownValue", {
        value: viewPoint,
        enumerable: true,
        configurable: true,
      });
      Object.defineProperty(this.viewPoint, "ownState", {
        value: viewPoint,
        enumerable: true,
        configurable: true,
      });
    }
  }

  protected onLayout(viewContext: ViewContextType<this>): void {
    super.onLayout(viewContext);
    const labelView = this.label.view;
    if (labelView !== null) {
      this.layoutLabel(labelView, this.viewFrame);
    }
  }

  protected layoutLabel(labelView: GraphicsView, frame: BoxR2): void {
    const placement = this.labelPlacement.state;
    // TODO: auto placement

    const size = Math.min(frame.width, frame.height);
    const padding = this.labelPadding.getValue().pxValue(size);
    const {x, y} = this.viewPoint.getValue();
    let y1 = y;
    if (placement === "top") {
      y1 -= padding;
    } else if (placement === "bottom") {
      y1 += padding;
    }

    if (TypesetView.is(labelView)) {
      labelView.textAlign.setState("center", View.Intrinsic);
      labelView.textBaseline.setState("bottom", View.Intrinsic);
      labelView.textOrigin.setState(new PointR2(x, y1), View.Intrinsic);
    }
  }

  protected doUpdateGeoBounds(): void {
    // nop
  }

  get viewBounds(): BoxR2 {
    const {x, y} = this.viewPoint.getValue();
    return new BoxR2(x, y, x, y);
  }

  get hitBounds(): BoxR2 {
    const {x, y} = this.viewPoint.getValue();
    const hitRadius = this.hitRadius.getStateOr(0);
    return new BoxR2(x - hitRadius, y - hitRadius, x + hitRadius, y + hitRadius);
  }

  protected doHitTest(x: number, y: number, viewContext: ViewContextType<this>): GraphicsView | null {
    let hit = super.doHitTest(x, y, viewContext);
    if (hit === null) {
      const renderer = viewContext.renderer;
      if (renderer instanceof CanvasRenderer) {
        const context = renderer.context;
        hit = this.hitTestPoint(x, y, context, this.viewFrame);
      }
    }
    return hit;
  }

  protected hitTestPoint(hx: number, hy: number, context: CanvasContext, frame: BoxR2): GraphicsView | null {
    const {x, y} = this.viewPoint.getValue();
    const radius = this.radius.value;

    let hitRadius = this.hitRadius.getStateOr(0);
    if (radius !== null) {
      const size = Math.min(frame.width, frame.height);
      hitRadius = Math.max(hitRadius, radius.pxValue(size));
    }

    const dx = x - hx;
    const dy = y - hy;
    if (dx * dx + dy * dy < hitRadius * hitRadius) {
      return this;
    }
    return null;
  }

  toAny(): GeoPointViewInit {
    const init: GeoPointViewInit = {};
    init.lng = this.geoPoint.value.lng;
    init.lat = this.geoPoint.value.lat;
    if (!this.viewPoint.isPrecedent(View.Intrinsic)) {
      init.x = this.viewPoint.value.x;
      init.y = this.viewPoint.value.y;
    }
    if (this.radius.value !== null) {
      init.radius = this.radius.value;
    }
    if (this.hitRadius.state !== void 0) {
      init.hitRadius = this.hitRadius.state;
    }
    if (this.color.value !== null) {
      init.color = this.color.value;
    }
    if (this.opacity.value !== void 0) {
      init.opacity = this.opacity.value;
    }
    if (this.labelPadding.value !== null) {
      init.labelPadding = this.labelPadding.value;
    }
    if (this.labelPlacement.state !== void 0) {
      init.labelPlacement = this.labelPlacement.state;
    }
    return init;
  }

  static create(): GeoPointView {
    return new GeoPointView();
  }

  static fromGeoPoint(point: AnyGeoPoint): GeoPointView {
    const view = new GeoPointView();
    view.setState(point);
    return view;
  }

  static fromInit(init: GeoPointViewInit): GeoPointView {
    const view = new GeoPointView();
    view.initView(init);
    return view;
  }

  static fromAny(value: AnyGeoPointView): GeoPointView {
    if (value instanceof GeoPointView) {
      return value;
    } else if (value instanceof GeoPoint || GeoPoint.isTuple(value)) {
      return this.fromGeoPoint(value);
    } else if (typeof value === "object" && value !== null) {
      return this.fromInit(value);
    }
    throw new TypeError("" + value);
  }
}
