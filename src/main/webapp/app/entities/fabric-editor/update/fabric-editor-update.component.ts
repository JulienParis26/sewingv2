import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FabricEditorFormService, FabricEditorFormGroup } from './fabric-editor-form.service';
import { IFabricEditor } from '../fabric-editor.model';
import { FabricEditorService } from '../service/fabric-editor.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { Editors } from 'app/entities/enumerations/editors.model';
import { Language } from 'app/entities/enumerations/language.model';

@Component({
  selector: 'jhi-fabric-editor-update',
  templateUrl: './fabric-editor-update.component.html',
})
export class FabricEditorUpdateComponent implements OnInit {
  isSaving = false;
  fabricEditor: IFabricEditor | null = null;
  editorsValues = Object.keys(Editors);
  languageValues = Object.keys(Language);

  editForm: FabricEditorFormGroup = this.fabricEditorFormService.createFabricEditorFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fabricEditorService: FabricEditorService,
    protected fabricEditorFormService: FabricEditorFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fabricEditor }) => {
      this.fabricEditor = fabricEditor;
      if (fabricEditor) {
        this.updateForm(fabricEditor);
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fabricEditor = this.fabricEditorFormService.getFabricEditor(this.editForm);
    if (fabricEditor.id !== null) {
      this.subscribeToSaveResponse(this.fabricEditorService.update(fabricEditor));
    } else {
      this.subscribeToSaveResponse(this.fabricEditorService.create(fabricEditor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFabricEditor>>): void {
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

  protected updateForm(fabricEditor: IFabricEditor): void {
    this.fabricEditor = fabricEditor;
    this.fabricEditorFormService.resetForm(this.editForm, fabricEditor);
  }
}
