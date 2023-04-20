import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMaterials } from '../materials.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../materials.test-samples';

import { MaterialsService } from './materials.service';

const requireRestSample: IMaterials = {
  ...sampleWithRequiredData,
};

describe('Materials Service', () => {
  let service: MaterialsService;
  let httpMock: HttpTestingController;
  let expectedResult: IMaterials | IMaterials[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MaterialsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Materials', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const materials = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(materials).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Materials', () => {
      const materials = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(materials).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Materials', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Materials', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Materials', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMaterialsToCollectionIfMissing', () => {
      it('should add a Materials to an empty array', () => {
        const materials: IMaterials = sampleWithRequiredData;
        expectedResult = service.addMaterialsToCollectionIfMissing([], materials);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(materials);
      });

      it('should not add a Materials to an array that contains it', () => {
        const materials: IMaterials = sampleWithRequiredData;
        const materialsCollection: IMaterials[] = [
          {
            ...materials,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMaterialsToCollectionIfMissing(materialsCollection, materials);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Materials to an array that doesn't contain it", () => {
        const materials: IMaterials = sampleWithRequiredData;
        const materialsCollection: IMaterials[] = [sampleWithPartialData];
        expectedResult = service.addMaterialsToCollectionIfMissing(materialsCollection, materials);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(materials);
      });

      it('should add only unique Materials to an array', () => {
        const materialsArray: IMaterials[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const materialsCollection: IMaterials[] = [sampleWithRequiredData];
        expectedResult = service.addMaterialsToCollectionIfMissing(materialsCollection, ...materialsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const materials: IMaterials = sampleWithRequiredData;
        const materials2: IMaterials = sampleWithPartialData;
        expectedResult = service.addMaterialsToCollectionIfMissing([], materials, materials2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(materials);
        expect(expectedResult).toContain(materials2);
      });

      it('should accept null and undefined values', () => {
        const materials: IMaterials = sampleWithRequiredData;
        expectedResult = service.addMaterialsToCollectionIfMissing([], null, materials, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(materials);
      });

      it('should return initial array if no Materials is added', () => {
        const materialsCollection: IMaterials[] = [sampleWithRequiredData];
        expectedResult = service.addMaterialsToCollectionIfMissing(materialsCollection, undefined, null);
        expect(expectedResult).toEqual(materialsCollection);
      });
    });

    describe('compareMaterials', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMaterials(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMaterials(entity1, entity2);
        const compareResult2 = service.compareMaterials(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMaterials(entity1, entity2);
        const compareResult2 = service.compareMaterials(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMaterials(entity1, entity2);
        const compareResult2 = service.compareMaterials(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
