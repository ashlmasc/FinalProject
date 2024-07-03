import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { MarkerService } from './marker.service';

@NgModule({
  declarations: [],
  imports: [BrowserModule, HttpClientModule],
  providers: [MarkerService],
  bootstrap: [],
})
export class AppModule {}
