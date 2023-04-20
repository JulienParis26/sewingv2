import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FabricEditorFormService } from './fabric-editor-form.service';
import { FabricEditorService } from '../service/fabric-editor.service';
import { IFabricEditor } from '../fabric-editor.model';

import { FabricEditorUpdateComponent } from './fabric-editor-update.component';

describe('FabricEditor Management Update Component', () => {
  let comp: FabricEditorUpdateComponent;
  let fixture: ComponentFixture<FabricEditorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fabricEditorFormService: FabricEditorFormService;
  let fabricEditorService: FabricEditorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FabricEditorUpdateComponent],
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
      .overrideTemplate(FabricEditorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FabricEditorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fabricEditorFormService = TestBed.inject(FabricEditorFormService);
    fabricEditorService = TestBed.inject(FabricEditorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fabricEditor: IFabricEditor = { id: 456 };

      activatedRoute.data = of({ fabricEditor });
      comp.ngOnInit();

      expect(comp.fabricEditor).toEqual(fabricEditor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricEditor>>();
      const fabricEditor = { id: 123 };
      jest.spyOn(fabricEditorFormService, 'getFabricEditor').mockReturnValue(fabricEditor);
      jest.spyOn(fabricEditorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricEditor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricEditor }));
      saveSubject.complete();

      // THEN
      expect(fabricEditorFormService.getFabricEditor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fabricEditorService.update).toHaveBeenCalledWith(expect.objectContaining(fabricEditor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricEditor>>();
      const fabricEditor = { id: 123 };
      jest.spyOn(fabricEditorFormService, 'getFabricEditor').mockReturnValue({ id: null });
      jest.spyOn(fabricEditorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricEditor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fabricEditor }));
      saveSubject.complete();

      // THEN
      expect(fabricEditorFormService.getFabricEditor).toHaveBeenCalled();
      expect(fabricEditorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFabricEditor>>();
      const fabricEditor = { id: 123 };
      jest.spyOn(fabricEditorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fabricEditor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fabricEditorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
