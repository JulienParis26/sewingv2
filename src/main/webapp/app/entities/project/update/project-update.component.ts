import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProjectFormService, ProjectFormGroup } from './project-form.service';
import { IProject } from '../project.model';
import { ProjectService } from '../service/project.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPatron } from 'app/entities/patron/patron.model';
import { PatronService } from 'app/entities/patron/service/patron.service';
import { IFabric } from 'app/entities/fabric/fabric.model';
import { FabricService } from 'app/entities/fabric/service/fabric.service';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  project: IProject | null = null;

  patronsSharedCollection: IPatron[] = [];
  fabricsSharedCollection: IFabric[] = [];

  editForm: ProjectFormGroup = this.projectFormService.createProjectFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected projectService: ProjectService,
    protected projectFormService: ProjectFormService,
    protected patronService: PatronService,
    protected fabricService: FabricService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePatron = (o1: IPatron | null, o2: IPatron | null): boolean => this.patronService.comparePatron(o1, o2);

  compareFabric = (o1: IFabric | null, o2: IFabric | null): boolean => this.fabricService.compareFabric(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.project = project;
      if (project) {
        this.updateForm(project);
      }

      this.loadRelationshipsOptions();
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
    const project = this.projectFormService.getProject(this.editForm);
    if (project.id !== null) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
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

  protected updateForm(project: IProject): void {
    this.project = project;
    this.projectFormService.resetForm(this.editForm, project);

    this.patronsSharedCollection = this.patronService.addPatronToCollectionIfMissing<IPatron>(this.patronsSharedCollection, project.patron);
    this.fabricsSharedCollection = this.fabricService.addFabricToCollectionIfMissing<IFabric>(
      this.fabricsSharedCollection,
      ...(project.fabrics ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.patronService
      .query()
      .pipe(map((res: HttpResponse<IPatron[]>) => res.body ?? []))
      .pipe(map((patrons: IPatron[]) => this.patronService.addPatronToCollectionIfMissing<IPatron>(patrons, this.project?.patron)))
      .subscribe((patrons: IPatron[]) => (this.patronsSharedCollection = patrons));

    this.fabricService
      .query()
      .pipe(map((res: HttpResponse<IFabric[]>) => res.body ?? []))
      .pipe(
        map((fabrics: IFabric[]) => this.fabricService.addFabricToCollectionIfMissing<IFabric>(fabrics, ...(this.project?.fabrics ?? [])))
      )
      .subscribe((fabrics: IFabric[]) => (this.fabricsSharedCollection = fabrics));
  }
}
