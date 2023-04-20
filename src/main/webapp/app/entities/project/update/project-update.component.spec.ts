import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectFormService } from './project-form.service';
import { ProjectService } from '../service/project.service';
import { IProject } from '../project.model';
import { IPatron } from 'app/entities/patron/patron.model';
import { PatronService } from 'app/entities/patron/service/patron.service';
import { IFabric } from 'app/entities/fabric/fabric.model';
import { FabricService } from 'app/entities/fabric/service/fabric.service';

import { ProjectUpdateComponent } from './project-update.component';

describe('Project Management Update Component', () => {
  let comp: ProjectUpdateComponent;
  let fixture: ComponentFixture<ProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectFormService: ProjectFormService;
  let projectService: ProjectService;
  let patronService: PatronService;
  let fabricService: FabricService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectFormService = TestBed.inject(ProjectFormService);
    projectService = TestBed.inject(ProjectService);
    patronService = TestBed.inject(PatronService);
    fabricService = TestBed.inject(FabricService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Patron query and add missing value', () => {
      const project: IProject = { id: 456 };
      const patron: IPatron = { id: 63248 };
      project.patron = patron;

      const patronCollection: IPatron[] = [{ id: 33596 }];
      jest.spyOn(patronService, 'query').mockReturnValue(of(new HttpResponse({ body: patronCollection })));
      const additionalPatrons = [patron];
      const expectedCollection: IPatron[] = [...additionalPatrons, ...patronCollection];
      jest.spyOn(patronService, 'addPatronToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(patronService.query).toHaveBeenCalled();
      expect(patronService.addPatronToCollectionIfMissing).toHaveBeenCalledWith(
        patronCollection,
        ...additionalPatrons.map(expect.objectContaining)
      );
      expect(comp.patronsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Fabric query and add missing value', () => {
      const project: IProject = { id: 456 };
      const fabrics: IFabric[] = [{ id: 23359 }];
      project.fabrics = fabrics;

      const fabricCollection: IFabric[] = [{ id: 9533 }];
      jest.spyOn(fabricService, 'query').mockReturnValue(of(new HttpResponse({ body: fabricCollection })));
      const additionalFabrics = [...fabrics];
      const expectedCollection: IFabric[] = [...additionalFabrics, ...fabricCollection];
      jest.spyOn(fabricService, 'addFabricToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(fabricService.query).toHaveBeenCalled();
      expect(fabricService.addFabricToCollectionIfMissing).toHaveBeenCalledWith(
        fabricCollection,
        ...additionalFabrics.map(expect.objectContaining)
      );
      expect(comp.fabricsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const project: IProject = { id: 456 };
      const patron: IPatron = { id: 28851 };
      project.patron = patron;
      const fabrics: IFabric = { id: 92303 };
      project.fabrics = [fabrics];

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(comp.patronsSharedCollection).toContain(patron);
      expect(comp.fabricsSharedCollection).toContain(fabrics);
      expect(comp.project).toEqual(project);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectFormService, 'getProject').mockReturnValue(project);
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectFormService.getProject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectService.update).toHaveBeenCalledWith(expect.objectContaining(project));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectFormService, 'getProject').mockReturnValue({ id: null });
      jest.spyOn(projectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectFormService.getProject).toHaveBeenCalled();
      expect(projectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePatron', () => {
      it('Should forward to patronService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(patronService, 'comparePatron');
        comp.comparePatron(entity, entity2);
        expect(patronService.comparePatron).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFabric', () => {
      it('Should forward to fabricService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fabricService, 'compareFabric');
        comp.compareFabric(entity, entity2);
        expect(fabricService.compareFabric).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
