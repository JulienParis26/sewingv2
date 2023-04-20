import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PatronFormService, PatronFormGroup } from './patron-form.service';
import { IPatron } from '../patron.model';
import { PatronService } from '../service/patron.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { PatronType } from 'app/entities/enumerations/patron-type.model';
import { Category } from 'app/entities/enumerations/category.model';
import { DifficultLevel } from 'app/entities/enumerations/difficult-level.model';
import { Qualification } from 'app/entities/enumerations/qualification.model';

@Component({
  selector: 'jhi-patron-update',
  templateUrl: './patron-update.component.html',
})
export class PatronUpdateComponent implements OnInit {
  isSaving = false;
  patron: IPatron | null = null;
  patronTypeValues = Object.keys(PatronType);
  categoryValues = Object.keys(Category);
  difficultLevelValues = Object.keys(DifficultLevel);
  qualificationValues = Object.keys(Qualification);

  editForm: PatronFormGroup = this.patronFormService.createPatronFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected patronService: PatronService,
    protected patronFormService: PatronFormService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patron }) => {
      this.patron = patron;
      if (patron) {
        this.updateForm(patron);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('couturev2App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const patron = this.patronFormService.getPatron(this.editForm);
    if (patron.id !== null) {
      this.subscribeToSaveResponse(this.patronService.update(patron));
    } else {
      this.subscribeToSaveResponse(this.patronService.create(patron));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatron>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(patron: IPatron): void {
    this.patron = patron;
    this.patronFormService.resetForm(this.editForm, patron);
  }
}
