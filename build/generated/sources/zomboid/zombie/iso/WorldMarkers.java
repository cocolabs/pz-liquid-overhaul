package zombie.iso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import zombie.GameTime;
import zombie.characters.IsoPlayer;
import zombie.core.Core;
import zombie.core.SpriteRenderer;
import zombie.core.math.PZMath;
import zombie.core.textures.ColorInfo;
import zombie.core.textures.Texture;
import zombie.iso.sprite.IsoSpriteInstance;
import zombie.iso.sprite.IsoSpriteManager;
import zombie.iso.weather.ClimateManager;
import zombie.network.GameServer;

public final class WorldMarkers {
   private static final float CIRCLE_TEXTURE_SCALE = 1.5F;
   public static final WorldMarkers instance = new WorldMarkers();
   private static int NextGridSquareMarkerID = 0;
   private static int NextHomingPointID = 0;
   private List gridSquareMarkers = new ArrayList();
   private WorldMarkers.PlayerHomingPointList[] homingPoints = new WorldMarkers.PlayerHomingPointList[4];
   private WorldMarkers.DirectionArrowList[] directionArrows = new WorldMarkers.DirectionArrowList[4];
   private static ColorInfo stCol = new ColorInfo();
   private final WorldMarkers.PlayerScreen playerScreen = new WorldMarkers.PlayerScreen();
   private WorldMarkers.Point intersectPoint = new WorldMarkers.Point(0.0F, 0.0F);
   private WorldMarkers.Point arrowStart = new WorldMarkers.Point(0.0F, 0.0F);
   private WorldMarkers.Point arrowEnd = new WorldMarkers.Point(0.0F, 0.0F);
   private WorldMarkers.Line arrowLine;

   private WorldMarkers() {
      this.arrowLine = new WorldMarkers.Line(this.arrowStart, this.arrowEnd);
   }

   public void init() {
      if (!GameServer.bServer) {
         int var1;
         for(var1 = 0; var1 < this.homingPoints.length; ++var1) {
            this.homingPoints[var1] = new WorldMarkers.PlayerHomingPointList();
         }

         for(var1 = 0; var1 < this.directionArrows.length; ++var1) {
            this.directionArrows[var1] = new WorldMarkers.DirectionArrowList();
         }

      }
   }

   public void reset() {
      int var1;
      for(var1 = 0; var1 < this.homingPoints.length; ++var1) {
         this.homingPoints[var1].clear();
      }

      for(var1 = 0; var1 < this.directionArrows.length; ++var1) {
         this.directionArrows[var1].clear();
      }

      this.gridSquareMarkers.clear();
   }

   private int GetDistance(int var1, int var2, int var3, int var4) {
      return (int)Math.sqrt(Math.pow((double)(var1 - var3), 2.0D) + Math.pow((double)(var2 - var4), 2.0D));
   }

   private float getAngle(int var1, int var2, int var3, int var4) {
      float var5 = (float)Math.toDegrees(Math.atan2((double)(var4 - var2), (double)(var3 - var1)));
      if (var5 < 0.0F) {
         var5 += 360.0F;
      }

      return var5;
   }

   private float angleDegrees(float var1) {
      if (var1 < 0.0F) {
         var1 += 360.0F;
      }

      if (var1 > 360.0F) {
         var1 -= 360.0F;
      }

      return var1;
   }

   public WorldMarkers.PlayerHomingPoint getHomingPoint(int var1) {
      for(int var2 = 0; var2 < this.homingPoints.length; ++var2) {
         for(int var3 = this.homingPoints[var2].size() - 1; var3 >= 0; ++var3) {
            if (((WorldMarkers.PlayerHomingPoint)this.homingPoints[var2].get(var3)).ID == var1) {
               return (WorldMarkers.PlayerHomingPoint)this.homingPoints[var2].get(var3);
            }
         }
      }

      return null;
   }

   public WorldMarkers.PlayerHomingPoint addPlayerHomingPoint(IsoPlayer var1, int var2, int var3) {
      return this.addPlayerHomingPoint(var1, var2, var3, "arrow_triangle", 1.0F, 1.0F, 1.0F, 1.0F, true, 20);
   }

   public WorldMarkers.PlayerHomingPoint addPlayerHomingPoint(IsoPlayer var1, int var2, int var3, float var4, float var5, float var6, float var7) {
      return this.addPlayerHomingPoint(var1, var2, var3, "arrow_triangle", var4, var5, var6, var7, true, 20);
   }

   public WorldMarkers.PlayerHomingPoint addPlayerHomingPoint(IsoPlayer var1, int var2, int var3, String var4, float var5, float var6, float var7, float var8, boolean var9, int var10) {
      if (GameServer.bServer) {
         return null;
      } else {
         WorldMarkers.PlayerHomingPoint var11 = new WorldMarkers.PlayerHomingPoint(var1.PlayerIndex);
         var11.setActive(true);
         var11.setTexture(var4);
         var11.setX(var2);
         var11.setY(var3);
         var11.setR(var5);
         var11.setG(var6);
         var11.setB(var7);
         var11.setA(var8);
         var11.setHomeOnTargetInView(var9);
         var11.setHomeOnTargetDist(var10);
         this.homingPoints[var1.PlayerIndex].add(var11);
         return var11;
      }
   }

   public boolean removeHomingPoint(WorldMarkers.PlayerHomingPoint var1) {
      return this.removeHomingPoint(var1.getID());
   }

   public boolean removeHomingPoint(int var1) {
      for(int var2 = 0; var2 < this.homingPoints.length; ++var2) {
         for(int var3 = this.homingPoints[var2].size() - 1; var3 >= 0; --var3) {
            if (((WorldMarkers.PlayerHomingPoint)this.homingPoints[var2].get(var3)).ID == var1) {
               ((WorldMarkers.PlayerHomingPoint)this.homingPoints[var2].get(var3)).remove();
               this.homingPoints[var2].remove(var3);
               return true;
            }
         }
      }

      return false;
   }

   public boolean removePlayerHomingPoint(IsoPlayer var1, WorldMarkers.PlayerHomingPoint var2) {
      return this.removePlayerHomingPoint(var1, var2.getID());
   }

   public boolean removePlayerHomingPoint(IsoPlayer var1, int var2) {
      for(int var3 = this.homingPoints[var1.PlayerIndex].size() - 1; var3 >= 0; --var3) {
         if (((WorldMarkers.PlayerHomingPoint)this.homingPoints[var1.PlayerIndex].get(var3)).ID == var2) {
            ((WorldMarkers.PlayerHomingPoint)this.homingPoints[var1.PlayerIndex].get(var3)).remove();
            this.homingPoints[var1.PlayerIndex].remove(var3);
            return true;
         }
      }

      return false;
   }

   public void removeAllHomingPoints(IsoPlayer var1) {
      this.homingPoints[var1.PlayerIndex].clear();
   }

   public WorldMarkers.DirectionArrow getDirectionArrow(int var1) {
      for(int var2 = 0; var2 < this.directionArrows.length; ++var2) {
         for(int var3 = this.directionArrows[var2].size() - 1; var3 >= 0; --var3) {
            if (((WorldMarkers.DirectionArrow)this.directionArrows[var2].get(var3)).ID == var1) {
               return (WorldMarkers.DirectionArrow)this.directionArrows[var2].get(var3);
            }
         }
      }

      return null;
   }

   public WorldMarkers.DirectionArrow addDirectionArrow(IsoPlayer var1, int var2, int var3, int var4, String var5, float var6, float var7, float var8, float var9) {
      if (GameServer.bServer) {
         return null;
      } else {
         WorldMarkers.DirectionArrow var10 = new WorldMarkers.DirectionArrow(var1.PlayerIndex);
         var10.setActive(true);
         var10.setTexture(var5);
         var10.setTexDown("dir_arrow_down");
         var10.setTexStairsUp("dir_arrow_stairs_up");
         var10.setTexStairsDown("dir_arrow_stairs_down");
         var10.setX(var2);
         var10.setY(var3);
         var10.setZ(var4);
         var10.setR(var6);
         var10.setG(var7);
         var10.setB(var8);
         var10.setA(var9);
         this.directionArrows[var1.PlayerIndex].add(var10);
         return var10;
      }
   }

   public boolean removeDirectionArrow(WorldMarkers.DirectionArrow var1) {
      return this.removeDirectionArrow(var1.getID());
   }

   public boolean removeDirectionArrow(int var1) {
      for(int var2 = 0; var2 < this.directionArrows.length; ++var2) {
         for(int var3 = this.directionArrows[var2].size() - 1; var3 >= 0; --var3) {
            if (((WorldMarkers.DirectionArrow)this.directionArrows[var2].get(var3)).ID == var1) {
               ((WorldMarkers.DirectionArrow)this.directionArrows[var2].get(var3)).remove();
               this.directionArrows[var2].remove(var3);
               return true;
            }
         }
      }

      return false;
   }

   public boolean removePlayerDirectionArrow(IsoPlayer var1, WorldMarkers.DirectionArrow var2) {
      return this.removePlayerDirectionArrow(var1, var2.getID());
   }

   public boolean removePlayerDirectionArrow(IsoPlayer var1, int var2) {
      for(int var3 = this.directionArrows[var1.PlayerIndex].size() - 1; var3 >= 0; --var3) {
         if (((WorldMarkers.DirectionArrow)this.directionArrows[var1.PlayerIndex].get(var3)).ID == var2) {
            ((WorldMarkers.DirectionArrow)this.directionArrows[var1.PlayerIndex].get(var3)).remove();
            this.directionArrows[var1.PlayerIndex].remove(var3);
            return true;
         }
      }

      return false;
   }

   public void removeAllDirectionArrows(IsoPlayer var1) {
      this.directionArrows[var1.PlayerIndex].clear();
   }

   public void update() {
      if (!GameServer.bServer) {
         this.updateGridSquareMarkers();
         this.updateHomingPoints();
         this.updateDirectionArrows();
      }
   }

   private void updateDirectionArrows() {
      for(int var1 = 0; var1 < this.directionArrows.length; ++var1) {
         if (this.directionArrows[var1].size() != 0) {
            int var2;
            for(var2 = this.directionArrows[var1].size() - 1; var2 >= 0; --var2) {
               if (((WorldMarkers.DirectionArrow)this.directionArrows[var1].get(var2)).isRemoved()) {
                  this.directionArrows[var1].remove(var2);
               }
            }

            this.playerScreen.update(var1);

            for(var2 = 0; var2 < this.directionArrows[var1].size(); ++var2) {
               WorldMarkers.DirectionArrow var3 = (WorldMarkers.DirectionArrow)this.directionArrows[var1].get(var2);
               if (var3.active && IsoPlayer.players[var1] != null) {
                  IsoPlayer var4 = IsoPlayer.players[var1];
                  if (var4.getSquare() != null) {
                     float var5 = Core.getInstance().getZoom(var1);
                     int var6 = var4.getSquare().getX();
                     int var7 = var4.getSquare().getY();
                     int var8 = var4.getSquare().getZ();
                     int var9 = this.GetDistance(var6, var7, var3.x, var3.y);
                     boolean var10 = false;
                     boolean var11 = false;
                     float var12 = 0.0F;
                     float var13 = 0.0F;
                     if (var9 < 300) {
                        var10 = true;
                        var12 = IsoUtils.XToScreenExact((float)var3.x, (float)var3.y, (float)var8, 0) / var5;
                        var13 = IsoUtils.YToScreenExact((float)var3.x, (float)var3.y, (float)var8, 0) / var5;
                        if (this.playerScreen.isWithinInner(var12, var13)) {
                           var11 = true;
                        }
                     }

                     if (var11) {
                        var3.renderWithAngle = false;
                        var3.isDrawOnWorld = false;
                        var3.renderSizeMod = 1.0F;
                        if (var5 > 1.0F) {
                           var3.renderSizeMod = var3.renderSizeMod / var5;
                        }

                        var3.renderScreenX = var12;
                        var3.renderScreenY = var13;
                        if (var8 == var3.z) {
                           var3.renderTexture = var3.texDown != null ? var3.texDown : var3.texture;
                        } else if (var3.z > var8) {
                           var3.renderTexture = var3.texStairsUp != null ? var3.texStairsUp : var3.texture;
                        } else {
                           var3.renderTexture = var3.texStairsDown != null ? var3.texStairsUp : var3.texture;
                        }

                        var3.lastWasWithinView = true;
                     } else {
                        var3.renderWithAngle = true;
                        var3.isDrawOnWorld = false;
                        var3.renderTexture = var3.texture;
                        var3.renderSizeMod = 1.0F;
                        float var14 = this.playerScreen.centerX;
                        float var15 = this.playerScreen.centerY;
                        float var16 = 0.0F;
                        if (!var10) {
                           var16 = this.getAngle(var3.x, var3.y, var6, var7);
                           var16 = this.angleDegrees(180.0F - var16);
                           var16 = this.angleDegrees(var16 + 45.0F);
                        } else {
                           var16 = this.getAngle((int)var14, (int)var15, (int)var12, (int)var13);
                           var16 = this.angleDegrees(180.0F - var16);
                           var16 = this.angleDegrees(var16 - 90.0F);
                        }

                        if (var16 != var3.angle) {
                           if (!var3.lastWasWithinView) {
                              var3.angle = PZMath.lerpAngle(PZMath.degToRad(var3.angle), PZMath.degToRad(var16), var3.angleLerpVal * GameTime.instance.getMultiplier());
                              var3.angle = PZMath.radToDeg(var3.angle);
                           } else {
                              var3.angle = var16;
                           }
                        }

                        float var17 = var14 + 32000.0F * (float)Math.sin(Math.toRadians((double)var3.angle));
                        float var18 = var15 + 32000.0F * (float)Math.cos(Math.toRadians((double)var3.angle));
                        var3.renderScreenX = var14;
                        var3.renderScreenY = var15;
                        this.arrowStart.set(var14, var15);
                        this.arrowEnd.set(var17, var18);
                        WorldMarkers.Line[] var19 = this.playerScreen.getBorders();

                        for(int var20 = 0; var20 < var19.length; ++var20) {
                           this.intersectPoint.set(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
                           if (intersectLineSegments(this.arrowLine, var19[var20], this.intersectPoint)) {
                              var3.renderScreenX = this.intersectPoint.x;
                              var3.renderScreenY = this.intersectPoint.y;
                              break;
                           }
                        }

                        var3.lastWasWithinView = false;
                     }
                  }
               }
            }
         }
      }

   }

   private void updateHomingPoints() {
      for(int var1 = 0; var1 < this.homingPoints.length; ++var1) {
         if (this.homingPoints[var1].size() != 0) {
            int var2;
            for(var2 = this.homingPoints[var1].size() - 1; var2 >= 0; --var2) {
               if (((WorldMarkers.PlayerHomingPoint)this.homingPoints[var1].get(var2)).isRemoved) {
                  this.homingPoints[var1].remove(var2);
               }
            }

            this.playerScreen.update(var1);

            for(var2 = 0; var2 < this.homingPoints[var1].size(); ++var2) {
               WorldMarkers.PlayerHomingPoint var3 = (WorldMarkers.PlayerHomingPoint)this.homingPoints[var1].get(var2);
               if (var3.active && IsoPlayer.players[var1] != null) {
                  IsoPlayer var4 = IsoPlayer.players[var1];
                  if (var4.getSquare() != null) {
                     float var5 = Core.getInstance().getZoom(var1);
                     var3.renderSizeMod = 1.0F;
                     if (var5 > 1.0F) {
                        var3.renderSizeMod = var3.renderSizeMod / var5;
                     }

                     int var6 = var4.getSquare().getX();
                     int var7 = var4.getSquare().getY();
                     var3.dist = this.GetDistance(var6, var7, var3.x, var3.y);
                     var3.targetOnScreen = false;
                     if ((float)var3.dist < 200.0F) {
                        var3.targetScreenX = IsoUtils.XToScreenExact((float)var3.x, (float)var3.y, 0.0F, 0) / var5;
                        var3.targetScreenY = IsoUtils.YToScreenExact((float)var3.x, (float)var3.y, 0.0F, 0) / var5;
                        var3.targetScreenX = var3.targetScreenX + var3.homeOnOffsetX / var5;
                        var3.targetScreenY = var3.targetScreenY + var3.homeOnOffsetY / var5;
                        var3.targetOnScreen = this.playerScreen.isOnScreen(var3.targetScreenX, var3.targetScreenY);
                     }

                     float var8 = this.playerScreen.centerX;
                     float var9 = var8 + var3.renderOffsetX / var5;
                     float var10 = this.playerScreen.centerY;
                     float var11 = var10 + var3.renderOffsetY / var5;
                     float var12;
                     if (!var3.customTargetAngle) {
                        var12 = 0.0F;
                        if (!var3.targetOnScreen) {
                           var12 = this.getAngle(var3.x, var3.y, var6, var7);
                           var12 = this.angleDegrees(180.0F - var12);
                           var12 = this.angleDegrees(var12 + 45.0F);
                        } else {
                           var12 = this.getAngle((int)var9, (int)var11, (int)var3.targetScreenX, (int)var3.targetScreenY);
                           var12 = this.angleDegrees(180.0F - var12);
                           var12 = this.angleDegrees(var12 - 90.0F);
                        }

                        var3.targetAngle = var12;
                     }

                     if (var3.targetAngle != var3.angle) {
                        var3.angle = PZMath.lerpAngle(PZMath.degToRad(var3.angle), PZMath.degToRad(var3.targetAngle), var3.angleLerpVal * GameTime.instance.getMultiplier());
                        var3.angle = PZMath.radToDeg(var3.angle);
                     }

                     var12 = var3.stickToCharDist / var5;
                     var3.targRenderX = var9 + var12 * (float)Math.sin(Math.toRadians((double)var3.angle));
                     var3.targRenderY = var11 + var12 * (float)Math.cos(Math.toRadians((double)var3.angle));
                     float var13 = var3.movementLerpVal;
                     if (var3.targetOnScreen) {
                        float var14 = (float)this.GetDistance((int)var3.targRenderX, (int)var3.targRenderY, (int)var3.targetScreenX, (int)var3.targetScreenY);
                        float var15 = (float)this.GetDistance((int)var9, (int)var11, (int)var3.targetScreenX, (int)var3.targetScreenY);
                        if (var15 < var14 || var3.homeOnTargetInView && var3.dist <= var3.homeOnTargetDist) {
                           var15 *= 0.75F;
                           var3.targRenderX = var9 + var15 * (float)Math.sin(Math.toRadians((double)var3.targetAngle));
                           var3.targRenderY = var11 + var15 * (float)Math.cos(Math.toRadians((double)var3.targetAngle));
                        }
                     }

                     var3.targRenderX = this.playerScreen.clampToInnerX(var3.targRenderX);
                     var3.targRenderY = this.playerScreen.clampToInnerY(var3.targRenderY);
                     if (var3.targRenderX != var3.renderX) {
                        var3.renderX = PZMath.lerp(var3.renderX, var3.targRenderX, var13 * GameTime.instance.getMultiplier());
                     }

                     if (var3.targRenderY != var3.renderY) {
                        var3.renderY = PZMath.lerp(var3.renderY, var3.targRenderY, var13 * GameTime.instance.getMultiplier());
                     }
                  }
               }
            }
         }
      }

   }

   private void updateGridSquareMarkers() {
      if (this.gridSquareMarkers.size() != 0) {
         int var1;
         for(var1 = this.gridSquareMarkers.size() - 1; var1 >= 0; --var1) {
            if (((WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var1)).isRemoved()) {
               this.gridSquareMarkers.remove(var1);
            }
         }

         for(var1 = 0; var1 < this.gridSquareMarkers.size(); ++var1) {
            WorldMarkers.GridSquareMarker var2 = (WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var1);
            if (var2.alphaInc) {
               var2.alpha = var2.alpha + GameTime.getInstance().getMultiplier() * var2.fadeSpeed;
               if (var2.alpha > var2.alphaMax) {
                  var2.alphaInc = false;
                  var2.alpha = var2.alphaMax;
               }
            } else {
               var2.alpha = var2.alpha - GameTime.getInstance().getMultiplier() * var2.fadeSpeed;
               if (var2.alpha < var2.alphaMin) {
                  var2.alphaInc = true;
                  var2.alpha = 0.3F;
               }
            }
         }

      }
   }

   public boolean removeGridSquareMarker(WorldMarkers.GridSquareMarker var1) {
      return this.removeGridSquareMarker(var1.getID());
   }

   public boolean removeGridSquareMarker(int var1) {
      for(int var2 = this.gridSquareMarkers.size() - 1; var2 >= 0; --var2) {
         if (((WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var2)).getID() == var1) {
            ((WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var2)).remove();
            this.gridSquareMarkers.remove(var2);
            return true;
         }
      }

      return false;
   }

   public WorldMarkers.GridSquareMarker getGridSquareMarker(int var1) {
      for(int var2 = 0; var2 < this.gridSquareMarkers.size(); ++var2) {
         if (((WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var2)).getID() == var1) {
            return (WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var2);
         }
      }

      return null;
   }

   public WorldMarkers.GridSquareMarker addGridSquareMarker(IsoGridSquare var1, float var2, float var3, float var4, boolean var5, float var6) {
      return this.addGridSquareMarker("circle_center", "circle_only_highlight", var1, var2, var3, var4, var5, var6, 0.006F, 0.3F, 1.0F);
   }

   public WorldMarkers.GridSquareMarker addGridSquareMarker(String var1, String var2, IsoGridSquare var3, float var4, float var5, float var6, boolean var7, float var8) {
      return this.addGridSquareMarker(var1, var2, var3, var4, var5, var6, var7, var8, 0.006F, 0.3F, 1.0F);
   }

   public WorldMarkers.GridSquareMarker addGridSquareMarker(String var1, String var2, IsoGridSquare var3, float var4, float var5, float var6, boolean var7, float var8, float var9, float var10, float var11) {
      if (GameServer.bServer) {
         return null;
      } else {
         WorldMarkers.GridSquareMarker var12 = new WorldMarkers.GridSquareMarker();
         var12.init(var1, var2, var3.x, var3.y, var3.z, var8);
         var12.setR(var4);
         var12.setG(var5);
         var12.setB(var6);
         var12.setA(1.0F);
         var12.setDoAlpha(var7);
         var12.setFadeSpeed(var9);
         var12.setAlpha(0.0F);
         var12.setAlphaMin(var10);
         var12.setAlphaMax(var11);
         this.gridSquareMarkers.add(var12);
         return var12;
      }
   }

   public void renderGridSquareMarkers(IsoCell.PerPlayerRender var1, int var2, int var3) {
      if (!GameServer.bServer && this.gridSquareMarkers.size() != 0) {
         IsoPlayer var4 = IsoPlayer.players[var3];
         if (var4 != null) {
            for(int var5 = 0; var5 < this.gridSquareMarkers.size(); ++var5) {
               WorldMarkers.GridSquareMarker var6 = (WorldMarkers.GridSquareMarker)this.gridSquareMarkers.get(var5);
               if (var6.z == (float)var2 && var6.z == var4.getZ() && var6.active) {
                  float var7 = 0.0F;
                  float var8 = 0.0F;
                  stCol.set(var6.r, var6.g, var6.b, var6.a);
                  if (var6.doBlink) {
                     var6.sprite.alpha = Core.blinkAlpha;
                  } else {
                     var6.sprite.alpha = var6.doAlpha ? var6.alpha : 1.0F;
                  }

                  var6.sprite.render((IsoObject)null, var6.x, var6.y, var6.z, IsoDirections.N, var7, var8, stCol);
                  if (var6.spriteOverlay != null) {
                     var6.spriteOverlay.alpha = 1.0F;
                     var6.spriteOverlay.render((IsoObject)null, var6.x, var6.y, var6.z, IsoDirections.N, var7, var8, stCol);
                  }
               }
            }

         }
      }
   }

   public void debugRender() {
   }

   public void render() {
      this.renderHomingPoint();
      this.renderDirectionArrow(false);
   }

   public void renderHomingPoint() {
      if (!GameServer.bServer) {
         for(int var1 = 0; var1 < this.homingPoints.length; ++var1) {
            if (this.homingPoints[var1].size() != 0) {
               for(int var2 = 0; var2 < this.homingPoints[var1].size(); ++var2) {
                  WorldMarkers.PlayerHomingPoint var3 = (WorldMarkers.PlayerHomingPoint)this.homingPoints[var1].get(var2);
                  if (var3.active && var3.texture != null) {
                     float var4 = 180.0F - var3.angle;
                     if (var4 < 0.0F) {
                        var4 += 360.0F;
                     }

                     float var5 = var3.a;
                     if (ClimateManager.getInstance().getFogIntensity() > 0.0F && var5 < 1.0F) {
                        float var6 = 1.0F - var5;
                        var5 += var6 * ClimateManager.getInstance().getFogIntensity() * 2.0F;
                        var5 = PZMath.clamp_01(var5);
                     }

                     this.DrawTextureAngle(var3.texture, var3.renderWidth, var3.renderHeight, (double)var3.renderX, (double)var3.renderY, (double)var4, var3.r, var3.g, var3.b, var5, var3.renderSizeMod);
                  }
               }
            }
         }

      }
   }

   public void renderDirectionArrow(boolean var1) {
      if (!GameServer.bServer) {
         for(int var2 = 0; var2 < this.directionArrows.length; ++var2) {
            if (this.directionArrows[var2].size() != 0) {
               for(int var3 = 0; var3 < this.directionArrows[var2].size(); ++var3) {
                  WorldMarkers.DirectionArrow var4 = (WorldMarkers.DirectionArrow)this.directionArrows[var2].get(var3);
                  if (var4.active && var4.renderTexture != null && var4.isDrawOnWorld == var1) {
                     float var5 = 0.0F;
                     if (var4.renderWithAngle) {
                        var5 = 180.0F - var4.angle;
                        if (var5 < 0.0F) {
                           var5 += 360.0F;
                        }
                     }

                     this.DrawTextureAngle(var4.renderTexture, var4.renderWidth, var4.renderHeight, (double)var4.renderScreenX, (double)var4.renderScreenY, (double)var5, var4.r, var4.g, var4.b, var4.a, var4.renderSizeMod);
                  }
               }
            }
         }

      }
   }

   private void DrawTextureAngle(Texture var1, float var2, float var3, double var4, double var6, double var8, float var10, float var11, float var12, float var13, float var14) {
      float var15 = var2 * var14 / 2.0F;
      float var16 = var3 * var14 / 2.0F;
      double var17 = Math.toRadians(180.0D + var8);
      double var19 = Math.cos(var17) * (double)var15;
      double var21 = Math.sin(var17) * (double)var15;
      double var23 = Math.cos(var17) * (double)var16;
      double var25 = Math.sin(var17) * (double)var16;
      double var27 = var19 - var25;
      double var29 = var23 + var21;
      double var31 = -var19 - var25;
      double var33 = var23 - var21;
      double var35 = -var19 + var25;
      double var37 = -var23 - var21;
      double var39 = var19 + var25;
      double var41 = -var23 + var21;
      var27 += var4;
      var29 += var6;
      var31 += var4;
      var33 += var6;
      var35 += var4;
      var37 += var6;
      var39 += var4;
      var41 += var6;
      SpriteRenderer.instance.render(var1, var27, var29, var31, var33, var35, var37, var39, var41, var10, var11, var12, var13, var10, var11, var12, var13, var10, var11, var12, var13, var10, var11, var12, var13, (Consumer)null);
   }

   public static boolean intersectLineSegments(WorldMarkers.Line var0, WorldMarkers.Line var1, WorldMarkers.Point var2) {
      float var3 = var0.s.x;
      float var4 = var0.s.y;
      float var5 = var0.e.x;
      float var6 = var0.e.y;
      float var7 = var1.s.x;
      float var8 = var1.s.y;
      float var9 = var1.e.x;
      float var10 = var1.e.y;
      float var11 = (var10 - var8) * (var5 - var3) - (var9 - var7) * (var6 - var4);
      if (var11 == 0.0F) {
         return false;
      } else {
         float var12 = var4 - var8;
         float var13 = var3 - var7;
         float var14 = ((var9 - var7) * var12 - (var10 - var8) * var13) / var11;
         if (!(var14 < 0.0F) && !(var14 > 1.0F)) {
            float var15 = ((var5 - var3) * var12 - (var6 - var4) * var13) / var11;
            if (!(var15 < 0.0F) && !(var15 > 1.0F)) {
               if (var2 != null) {
                  var2.set(var3 + (var5 - var3) * var14, var4 + (var6 - var4) * var14);
               }

               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   private static class Line {
      WorldMarkers.Point s;
      WorldMarkers.Point e;

      Line(WorldMarkers.Point var1, WorldMarkers.Point var2) {
         this.s = var1;
         this.e = var2;
      }

      public String toString() {
         return String.format("{s: %s, e: %s}", this.s.toString(), this.e.toString());
      }
   }

   private static class Point {
      float x;
      float y;

      Point(float var1, float var2) {
         this.x = var1;
         this.y = var2;
      }

      public WorldMarkers.Point set(float var1, float var2) {
         this.x = var1;
         this.y = var2;
         return this;
      }

      public boolean notInfinite() {
         return !Float.isInfinite(this.x) && !Float.isInfinite(this.y);
      }

      public String toString() {
         return String.format("{%f, %f}", this.x, this.y);
      }
   }

   class PlayerScreen {
      private float centerX;
      private float centerY;
      private float x;
      private float y;
      private float width;
      private float height;
      private float padTop = 100.0F;
      private float padLeft = 100.0F;
      private float padBot = 100.0F;
      private float padRight = 100.0F;
      private float innerX;
      private float innerY;
      private float innerX2;
      private float innerY2;
      private WorldMarkers.Line borderTop = new WorldMarkers.Line(new WorldMarkers.Point(0.0F, 0.0F), new WorldMarkers.Point(0.0F, 0.0F));
      private WorldMarkers.Line borderRight = new WorldMarkers.Line(new WorldMarkers.Point(0.0F, 0.0F), new WorldMarkers.Point(0.0F, 0.0F));
      private WorldMarkers.Line borderBot = new WorldMarkers.Line(new WorldMarkers.Point(0.0F, 0.0F), new WorldMarkers.Point(0.0F, 0.0F));
      private WorldMarkers.Line borderLeft = new WorldMarkers.Line(new WorldMarkers.Point(0.0F, 0.0F), new WorldMarkers.Point(0.0F, 0.0F));
      private WorldMarkers.Line[] borders = new WorldMarkers.Line[4];

      private void update(int var1) {
         this.x = (float)IsoCamera.getScreenLeft(var1);
         this.y = (float)IsoCamera.getScreenTop(var1);
         this.width = (float)IsoCamera.getScreenWidth(var1);
         this.height = (float)IsoCamera.getScreenHeight(var1);
         this.centerX = this.x + this.width / 2.0F;
         this.centerY = this.y + this.height / 2.0F;
         this.innerX = this.x + this.padLeft;
         this.innerY = this.y + this.padTop;
         float var2 = this.width - (this.padLeft + this.padRight);
         float var3 = this.height - (this.padTop + this.padBot);
         this.innerX2 = this.innerX + var2;
         this.innerY2 = this.innerY + var3;
      }

      private WorldMarkers.Line[] getBorders() {
         this.borders[0] = this.getBorderTop();
         this.borders[1] = this.getBorderRight();
         this.borders[2] = this.getBorderBot();
         this.borders[3] = this.getBorderLeft();
         return this.borders;
      }

      private WorldMarkers.Line getBorderTop() {
         this.borderTop.s.set(this.innerX, this.innerY);
         this.borderTop.e.set(this.innerX2, this.innerY);
         return this.borderTop;
      }

      private WorldMarkers.Line getBorderRight() {
         this.borderRight.s.set(this.innerX2, this.innerY);
         this.borderRight.e.set(this.innerX2, this.innerY2);
         return this.borderRight;
      }

      private WorldMarkers.Line getBorderBot() {
         this.borderBot.s.set(this.innerX, this.innerY2);
         this.borderBot.e.set(this.innerX2, this.innerY2);
         return this.borderBot;
      }

      private WorldMarkers.Line getBorderLeft() {
         this.borderLeft.s.set(this.innerX, this.innerY);
         this.borderLeft.e.set(this.innerX, this.innerY2);
         return this.borderLeft;
      }

      private float clampToInnerX(float var1) {
         return PZMath.clamp(var1, this.innerX, this.innerX2);
      }

      private float clampToInnerY(float var1) {
         return PZMath.clamp(var1, this.innerY, this.innerY2);
      }

      private boolean isOnScreen(float var1, float var2) {
         return var1 >= this.x && var1 < this.x + this.width && var2 >= this.y && var2 < this.y + this.height;
      }

      private boolean isWithinInner(float var1, float var2) {
         return var1 >= this.innerX && var1 < this.innerX2 && var2 >= this.innerY && var2 < this.innerY2;
      }
   }

   class DirectionArrowList extends ArrayList {
   }

   public class DirectionArrow {
      public static final boolean doDebug = false;
      private WorldMarkers.DirectionArrow.DebugStuff debugStuff;
      private int ID;
      private boolean active = true;
      private boolean isRemoved = false;
      private boolean isDrawOnWorld = false;
      private Texture renderTexture;
      private Texture texture;
      private Texture texStairsUp;
      private Texture texStairsDown;
      private Texture texDown;
      private int x;
      private int y;
      private int z;
      private float r;
      private float g;
      private float b;
      private float a;
      private float renderWidth = 32.0F;
      private float renderHeight = 32.0F;
      private float angle;
      private float angleLerpVal = 0.25F;
      private boolean lastWasWithinView = true;
      private float renderScreenX;
      private float renderScreenY;
      private boolean renderWithAngle = true;
      private float renderSizeMod = 1.0F;

      public DirectionArrow(int var2) {
         if (Core.bDebug) {
         }

         this.ID = WorldMarkers.NextHomingPointID++;
      }

      public void setTexture(String var1) {
         if (var1 == null) {
            var1 = "dir_arrow_up";
         }

         this.texture = Texture.getSharedTexture("media/textures/highlights/" + var1 + ".png");
      }

      public void setTexDown(String var1) {
         this.texDown = Texture.getSharedTexture("media/textures/highlights/" + var1 + ".png");
      }

      public void setTexStairsDown(String var1) {
         this.texStairsDown = Texture.getSharedTexture("media/textures/highlights/" + var1 + ".png");
      }

      public void setTexStairsUp(String var1) {
         this.texStairsUp = Texture.getSharedTexture("media/textures/highlights/" + var1 + ".png");
      }

      public void remove() {
         this.isRemoved = true;
      }

      public boolean isRemoved() {
         return this.isRemoved;
      }

      public boolean isActive() {
         return this.active;
      }

      public void setActive(boolean var1) {
         this.active = var1;
      }

      public float getR() {
         return this.r;
      }

      public void setR(float var1) {
         this.r = var1;
      }

      public float getB() {
         return this.b;
      }

      public void setB(float var1) {
         this.b = var1;
      }

      public float getG() {
         return this.g;
      }

      public void setG(float var1) {
         this.g = var1;
      }

      public float getA() {
         return this.a;
      }

      public void setA(float var1) {
         this.a = var1;
      }

      public int getID() {
         return this.ID;
      }

      public int getX() {
         return this.x;
      }

      public void setX(int var1) {
         this.x = var1;
      }

      public int getY() {
         return this.y;
      }

      public void setY(int var1) {
         this.y = var1;
      }

      public int getZ() {
         return this.z;
      }

      public void setZ(int var1) {
         this.z = var1;
      }

      public float getRenderWidth() {
         return this.renderWidth;
      }

      public void setRenderWidth(float var1) {
         this.renderWidth = var1;
      }

      public float getRenderHeight() {
         return this.renderHeight;
      }

      public void setRenderHeight(float var1) {
         this.renderHeight = var1;
      }

      private class DebugStuff {
         private float centerX;
         private float centerY;
         private float endX;
         private float endY;

         private DebugStuff() {
         }

         // $FF: synthetic method
         DebugStuff(Object var2) {
            this();
         }
      }
   }

   class PlayerHomingPointList extends ArrayList {
   }

   public static class PlayerHomingPoint {
      private int ID;
      private Texture texture;
      private int x;
      private int y;
      private float r;
      private float g;
      private float b;
      private float a;
      private float angle = 0.0F;
      private float targetAngle = 0.0F;
      private boolean customTargetAngle = false;
      private float angleLerpVal = 0.25F;
      private float movementLerpVal = 0.25F;
      private int dist = 0;
      private float targRenderX = (float)Core.getInstance().getScreenWidth() / 2.0F;
      private float targRenderY = (float)Core.getInstance().getScreenHeight() / 2.0F;
      private float renderX;
      private float renderY;
      private float renderOffsetX;
      private float renderOffsetY;
      private float renderWidth;
      private float renderHeight;
      private float renderSizeMod;
      private float targetScreenX;
      private float targetScreenY;
      private boolean targetOnScreen;
      private float stickToCharDist;
      private boolean active;
      private boolean homeOnTargetInView;
      private int homeOnTargetDist;
      private float homeOnOffsetX;
      private float homeOnOffsetY;
      private boolean isRemoved;

      public PlayerHomingPoint(int var1) {
         this.renderX = this.targRenderX;
         this.renderY = this.targRenderY;
         this.renderOffsetX = 0.0F;
         this.renderOffsetY = 50.0F;
         this.renderWidth = 32.0F;
         this.renderHeight = 32.0F;
         this.renderSizeMod = 1.0F;
         this.targetOnScreen = false;
         this.stickToCharDist = 130.0F;
         this.homeOnTargetInView = true;
         this.homeOnTargetDist = 20;
         this.homeOnOffsetX = 0.0F;
         this.homeOnOffsetY = 0.0F;
         this.isRemoved = false;
         this.ID = WorldMarkers.NextHomingPointID++;
         float var2 = (float)IsoCamera.getScreenLeft(var1);
         float var3 = (float)IsoCamera.getScreenTop(var1);
         float var4 = (float)IsoCamera.getScreenWidth(var1);
         float var5 = (float)IsoCamera.getScreenHeight(var1);
         this.targRenderX = var2 + var4 / 2.0F;
         this.targRenderY = var3 + var5 / 2.0F;
      }

      public void setTexture(String var1) {
         if (var1 == null) {
            var1 = "arrow_triangle";
         }

         this.texture = Texture.getSharedTexture("media/textures/highlights/" + var1 + ".png");
      }

      public void remove() {
         this.isRemoved = true;
      }

      public boolean isRemoved() {
         return this.isRemoved;
      }

      public boolean isActive() {
         return this.active;
      }

      public void setActive(boolean var1) {
         this.active = var1;
      }

      public float getR() {
         return this.r;
      }

      public void setR(float var1) {
         this.r = var1;
      }

      public float getB() {
         return this.b;
      }

      public void setB(float var1) {
         this.b = var1;
      }

      public float getG() {
         return this.g;
      }

      public void setG(float var1) {
         this.g = var1;
      }

      public float getA() {
         return this.a;
      }

      public void setA(float var1) {
         this.a = var1;
      }

      public int getHomeOnTargetDist() {
         return this.homeOnTargetDist;
      }

      public void setHomeOnTargetDist(int var1) {
         this.homeOnTargetDist = var1;
      }

      public int getID() {
         return this.ID;
      }

      public float getTargetAngle() {
         return this.targetAngle;
      }

      public void setTargetAngle(float var1) {
         this.targetAngle = var1;
      }

      public boolean isCustomTargetAngle() {
         return this.customTargetAngle;
      }

      public void setCustomTargetAngle(boolean var1) {
         this.customTargetAngle = var1;
      }

      public int getX() {
         return this.x;
      }

      public void setX(int var1) {
         this.x = var1;
      }

      public int getY() {
         return this.y;
      }

      public void setY(int var1) {
         this.y = var1;
      }

      public float getAngleLerpVal() {
         return this.angleLerpVal;
      }

      public void setAngleLerpVal(float var1) {
         this.angleLerpVal = var1;
      }

      public float getMovementLerpVal() {
         return this.movementLerpVal;
      }

      public void setMovementLerpVal(float var1) {
         this.movementLerpVal = var1;
      }

      public boolean isHomeOnTargetInView() {
         return this.homeOnTargetInView;
      }

      public void setHomeOnTargetInView(boolean var1) {
         this.homeOnTargetInView = var1;
      }

      public float getRenderWidth() {
         return this.renderWidth;
      }

      public void setRenderWidth(float var1) {
         this.renderWidth = var1;
      }

      public float getRenderHeight() {
         return this.renderHeight;
      }

      public void setRenderHeight(float var1) {
         this.renderHeight = var1;
      }

      public float getStickToCharDist() {
         return this.stickToCharDist;
      }

      public void setStickToCharDist(float var1) {
         this.stickToCharDist = var1;
      }

      public float getRenderOffsetX() {
         return this.renderOffsetX;
      }

      public void setRenderOffsetX(float var1) {
         this.renderOffsetX = var1;
      }

      public float getRenderOffsetY() {
         return this.renderOffsetY;
      }

      public void setRenderOffsetY(float var1) {
         this.renderOffsetY = var1;
      }

      public float getHomeOnOffsetX() {
         return this.homeOnOffsetX;
      }

      public void setHomeOnOffsetX(float var1) {
         this.homeOnOffsetX = var1;
      }

      public float getHomeOnOffsetY() {
         return this.homeOnOffsetY;
      }

      public void setHomeOnOffsetY(float var1) {
         this.homeOnOffsetY = var1;
      }

      public void setTableSurface() {
         this.homeOnOffsetY = -30.0F * (float)Core.TileScale;
      }

      public void setHighCounter() {
         this.homeOnOffsetY = -50.0F * (float)Core.TileScale;
      }

      public void setYOffsetScaled(float var1) {
         this.homeOnOffsetY = var1 * (float)Core.TileScale;
      }

      public void setXOffsetScaled(float var1) {
         this.homeOnOffsetX = var1 * (float)Core.TileScale;
      }
   }

   public static final class GridSquareMarker {
      private int ID;
      private IsoSpriteInstance sprite;
      private IsoSpriteInstance spriteOverlay;
      private float orig_x;
      private float orig_y;
      private float orig_z;
      private float x;
      private float y;
      private float z;
      private float scaleRatio;
      private float r;
      private float g;
      private float b;
      private float a;
      private float size;
      private boolean doBlink = false;
      private boolean doAlpha;
      private boolean bScaleCircleTexture = false;
      private float fadeSpeed = 0.006F;
      private float alpha = 0.0F;
      private float alphaMax = 1.0F;
      private float alphaMin = 0.3F;
      private boolean alphaInc = true;
      private boolean active = true;
      private boolean isRemoved = false;

      public GridSquareMarker() {
         this.ID = WorldMarkers.NextGridSquareMarkerID++;
      }

      public int getID() {
         return this.ID;
      }

      public void remove() {
         this.isRemoved = true;
      }

      public boolean isRemoved() {
         return this.isRemoved;
      }

      public void init(String var1, String var2, int var3, int var4, int var5, float var6) {
         if (var1 == null) {
            var1 = "circle_center";
         }

         Texture var7 = Texture.getSharedTexture("media/textures/highlights/" + var1 + ".png");
         float var8 = (float)var7.getWidth();
         float var9 = 64.0F * (float)Core.TileScale;
         this.scaleRatio = 1.0F / (var8 / var9);
         this.sprite = new IsoSpriteInstance(IsoSpriteManager.instance.getSprite("media/textures/highlights/" + var1 + ".png"));
         if (var2 != null) {
            this.spriteOverlay = new IsoSpriteInstance(IsoSpriteManager.instance.getSprite("media/textures/highlights/" + var2 + ".png"));
         }

         this.setPosAndSize(var3, var4, var5, var6);
      }

      public void setPosAndSize(int var1, int var2, int var3, float var4) {
         float var5 = var4 * (this.bScaleCircleTexture ? 1.5F : 1.0F);
         float var6 = this.scaleRatio * var5;
         this.sprite.setScale(var6, var6);
         if (this.spriteOverlay != null) {
            this.spriteOverlay.setScale(var6, var6);
         }

         this.size = var4;
         this.orig_x = (float)var1;
         this.orig_y = (float)var2;
         this.orig_z = (float)var3;
         this.x = (float)var1 - (var5 - 0.5F);
         this.y = (float)var2 + 0.5F;
         this.z = (float)var3;
      }

      public void setPos(int var1, int var2, int var3) {
         float var4 = this.size * (this.bScaleCircleTexture ? 1.5F : 1.0F);
         this.orig_x = (float)var1;
         this.orig_y = (float)var2;
         this.orig_z = (float)var3;
         this.x = (float)var1 - (var4 - 0.5F);
         this.y = (float)var2 + 0.5F;
         this.z = (float)var3;
      }

      public void setSize(float var1) {
         float var2 = var1 * (this.bScaleCircleTexture ? 1.5F : 1.0F);
         float var3 = this.scaleRatio * var2;
         this.sprite.setScale(var3, var3);
         if (this.spriteOverlay != null) {
            this.spriteOverlay.setScale(var3, var3);
         }

         this.size = var1;
         this.x = this.orig_x - (var2 - 0.5F);
         this.y = this.orig_y + 0.5F;
         this.z = this.orig_z;
      }

      public boolean isActive() {
         return this.active;
      }

      public void setActive(boolean var1) {
         this.active = var1;
      }

      public float getSize() {
         return this.size;
      }

      public float getX() {
         return this.x;
      }

      public float getY() {
         return this.y;
      }

      public float getZ() {
         return this.z;
      }

      public float getR() {
         return this.r;
      }

      public void setR(float var1) {
         this.r = var1;
      }

      public float getG() {
         return this.g;
      }

      public void setG(float var1) {
         this.g = var1;
      }

      public float getB() {
         return this.b;
      }

      public void setB(float var1) {
         this.b = var1;
      }

      public float getA() {
         return this.a;
      }

      public void setA(float var1) {
         this.a = var1;
      }

      public float getAlpha() {
         return this.alpha;
      }

      public void setAlpha(float var1) {
         this.alpha = var1;
      }

      public float getAlphaMax() {
         return this.alphaMax;
      }

      public void setAlphaMax(float var1) {
         this.alphaMax = var1;
      }

      public float getAlphaMin() {
         return this.alphaMin;
      }

      public void setAlphaMin(float var1) {
         this.alphaMin = var1;
      }

      public boolean isDoAlpha() {
         return this.doAlpha;
      }

      public void setDoAlpha(boolean var1) {
         this.doAlpha = var1;
      }

      public float getFadeSpeed() {
         return this.fadeSpeed;
      }

      public void setFadeSpeed(float var1) {
         this.fadeSpeed = var1;
      }

      public boolean isDoBlink() {
         return this.doBlink;
      }

      public void setDoBlink(boolean var1) {
         this.doBlink = var1;
      }

      public boolean isScaleCircleTexture() {
         return this.bScaleCircleTexture;
      }

      public void setScaleCircleTexture(boolean var1) {
         this.bScaleCircleTexture = var1;
         float var2 = this.size * (this.bScaleCircleTexture ? 1.5F : 1.0F);
         float var3 = this.scaleRatio * var2;
         if (this.sprite != null) {
            this.sprite.setScale(var3, var3);
         }

         if (this.spriteOverlay != null) {
            this.spriteOverlay.setScale(var3, var3);
         }

         this.x = this.orig_x - (var2 - 0.5F);
      }
   }
}
